package com.interblocks.iwallet.notifyr.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "notifyr.tcp-server")
public class TCPConnectionProperties {
    private String ip;
    private int port;
    private int requestTimeOut;
    private int responseTimeOut;
}
