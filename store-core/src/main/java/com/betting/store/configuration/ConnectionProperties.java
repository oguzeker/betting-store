package com.betting.store.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "connection")
public class ConnectionProperties {

    private int timeout;
    private int maxConnectionCount;
    private int timeToLive;
    private int maxRedirectionCount;

}
