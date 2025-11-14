package com.islamicapp.muslimlife.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "http-client")
public class HttpClientProperties {
    private int connectionTimeout = 30000;
    private int readTimeout = 10000;
    private int maxConnections = 100;
    private int maxPerRoute = 20;
    private int connectionRequestTimeout = 30000;
    private int idleConnectionTimeout = 60000;
}
