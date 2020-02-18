package com.interblocks.iwallet.notifyr;

import com.interblocks.iwallet.notifyr.config.props.EmailDispatcherProperties;
import com.interblocks.iwallet.notifyr.config.props.JAXBProperties;
import com.interblocks.iwallet.notifyr.config.props.SMSDispatcherProperties;
import com.interblocks.iwallet.notifyr.config.props.TCPConnectionProperties;
import com.interblocks.iwallet.notifyr.core.dispatchers.facades.DispatcherFacade;
import com.interblocks.iwallet.notifyr.core.tcp.TCPServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@EnableConfigurationProperties({
        EmailDispatcherProperties.class, TCPConnectionProperties.class,
        SMSDispatcherProperties.class, JAXBProperties.class})
@SpringBootApplication
public class NotifyrApplication implements ApplicationRunner {

    private final TCPServer tcpServer;
    private final DispatcherFacade dispatcherFacade;

    @Autowired
    public NotifyrApplication(TCPServer tcpServer, DispatcherFacade dispatcherFacade) {
        this.tcpServer = tcpServer;
        this.dispatcherFacade = dispatcherFacade;
    }

    public static void main(String[] args) {
        SpringApplication.run(NotifyrApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.dispatcherFacade.startAll();
        this.tcpServer.start();
    }
}
