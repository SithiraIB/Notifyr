package com.interblocks.iwallet.notifyr.core.tcp;

import com.interblocks.iwallet.notifyr.config.props.TCPConnectionProperties;
import com.interblocks.iwallet.notifyr.core.tcp.handlers.IdleSocketHandler;
import com.interblocks.iwallet.notifyr.core.tcp.handlers.IncomingMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TCPServer {

    private final IdleSocketHandler idleSocketHandler;
    private final IncomingMessageHandler incomingMessageHandler;
    private final TCPConnectionProperties tcpConnectionProperties;

    private NioEventLoopGroup connectionAcceptorThreadGroup;
    private NioEventLoopGroup connectionProcessorThreadGroup;
    private ChannelFuture serverSocketFuture;

    @Autowired
    public TCPServer(IdleSocketHandler idleSocketHandler, IncomingMessageHandler incomingMessageHandler, TCPConnectionProperties tcpConnectionProperties) {
        this.idleSocketHandler = idleSocketHandler;
        this.incomingMessageHandler = incomingMessageHandler;
        this.tcpConnectionProperties = tcpConnectionProperties;
    }

    @Async
    public void start() throws InterruptedException {
        log.info("XML Listener started on [{}:{}]", tcpConnectionProperties.getIp(), tcpConnectionProperties.getPort());
        try {
            this.connectionAcceptorThreadGroup = new NioEventLoopGroup();
            this.connectionProcessorThreadGroup = new NioEventLoopGroup();

            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap
                    .group(this.connectionAcceptorThreadGroup, this.connectionProcessorThreadGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast("requestHandler", incomingMessageHandler);
                            socketChannel.pipeline().addLast("idleStateHandler",
                                    new IdleStateHandler(tcpConnectionProperties.getRequestTimeOut(), tcpConnectionProperties.getResponseTimeOut(), 0, TimeUnit.MILLISECONDS));
                            socketChannel.pipeline().addLast("idleSocketHandler", idleSocketHandler);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            this.serverSocketFuture = serverBootstrap.bind(tcpConnectionProperties.getIp(), tcpConnectionProperties.getPort()).sync();
            serverSocketFuture.channel().closeFuture().sync();

            log.info("Server is listing for Service Requests on [{}:{}]", tcpConnectionProperties.getIp(), tcpConnectionProperties.getPort());
        } finally {
            connectionAcceptorThreadGroup.shutdownGracefully();
            connectionProcessorThreadGroup.shutdownGracefully();
        }
    }

    /**
     * Stop server and all processing.
     */
    public void stop() {
        log.info("Stopping server...");
        this.serverSocketFuture.channel().close();
        this.connectionAcceptorThreadGroup.shutdownGracefully();
        this.connectionProcessorThreadGroup.shutdownGracefully();
    }

}
