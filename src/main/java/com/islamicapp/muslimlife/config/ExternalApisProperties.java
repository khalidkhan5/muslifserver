package com.islamicapp.muslimlife.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "external-apis")
public class ExternalApisProperties {

    // Remove the "configs" map wrapper - directly map services
    private ApiConfig userService;
    private ApiConfig paymentService;
    private ApiConfig notificationService;
    private ApiConfig metalService;
    private ApiConfig googleService;

    @Data
    public static class ApiConfig {
        private String baseUrl;
    }
}