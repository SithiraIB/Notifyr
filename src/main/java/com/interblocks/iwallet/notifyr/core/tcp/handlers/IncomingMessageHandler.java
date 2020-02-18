package com.interblocks.iwallet.notifyr.core.tcp.handlers;

import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import com.interblocks.iwallet.notifyr.core.jaxb.JAXBParser;
import com.interblocks.iwallet.notifyr.core.services.impl.EmailService;
import com.interblocks.iwallet.notifyr.core.services.impl.SMSService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@ChannelHandler.Sharable
public class IncomingMessageHandler extends ChannelInboundHandlerAdapter {

    private final JAXBParser jaxbParser;
    private final EmailService emailService;
    private final SMSService smsService;

    @Autowired
    public IncomingMessageHandler(JAXBParser jaxbParser, EmailService emailService, SMSService smsService) {
        this.jaxbParser = jaxbParser;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        log.info("Message received from client: {}", ctx.channel().id().asShortText());
        final ByteBuf byteBuf = (ByteBuf) msg;
        final String xmlMessage = byteBuf.toString(StandardCharsets.UTF_8);
        DefaultMessage defaultMessage = jaxbParser.unmarshal(xmlMessage, DefaultMessage.class);

        // perform logic

        ByteBuf response = Unpooled.wrappedBuffer("test-me".getBytes());
        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
