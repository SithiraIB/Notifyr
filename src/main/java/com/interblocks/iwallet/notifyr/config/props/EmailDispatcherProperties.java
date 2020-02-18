package com.interblocks.iwallet.notifyr.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "notifyr.dispatchers.email")
public class EmailDispatcherProperties {
    private boolean enabled;
    private int maxQueueCapacity;
    private EndPoint endPoint;

    @Data
    private static class EndPoint {
        private String url;
        private String username;
        private String password;
    }
}
