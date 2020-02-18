package com.interblocks.iwallet.notifyr.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "notifyr.jaxb")
public class JAXBProperties {
    private Class[] classes;
}
