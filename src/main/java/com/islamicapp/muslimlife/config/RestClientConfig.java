package com.islamicapp.muslimlife.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ExternalApisProperties.class)
public class RestClientConfig {

    private final HttpClientProperties httpClientProperties;
    private final ExternalApisProperties externalApisProperties;

    // Shared connection pool for all clients
    @Bean
    public PoolingHttpClientConnectionManager connectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        connectionManager.setMaxTotal(httpClientProperties.getMaxConnections());
        connectionManager.setDefaultMaxPerRoute(httpClientProperties.getMaxPerRoute());

        log.info("Connection pool configured - MaxTotal: {}, MaxPerRoute: {}",
                httpClientProperties.getMaxConnections(), httpClientProperties.getMaxPerRoute());

        return connectionManager;
    }

    // Shared HttpClient
    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager connectionManager) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(httpClientProperties.getConnectionRequestTimeout()))
                .setResponseTimeout(Timeout.ofMilliseconds(httpClientProperties.getReadTimeout()))
                .build();

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .evictIdleConnections(org.apache.hc.core5.util.TimeValue.ofMilliseconds(httpClientProperties.getIdleConnectionTimeout()))
                .build();
    }

    // Payment Service RestClient
    @Bean("paymentServiceRestClient")
    public RestClient paymentServiceRestClient(CloseableHttpClient httpClient) {
        return createRestClient(httpClient, externalApisProperties.getPaymentService(), "PaymentService");
    }

    // Notification Service RestClient
    @Bean("notificationServiceRestClient")
    public RestClient notificationServiceRestClient(CloseableHttpClient httpClient) {
        return createRestClient(httpClient, externalApisProperties.getNotificationService(), "NotificationService");
    }

    @Bean("userServiceRestClient")
    public RestClient userServiceRestClient(CloseableHttpClient httpClient) {
        return createRestClient(httpClient, externalApisProperties.getUserService(), "UserService");
    }

    @Bean("metalPriceRestClient")
    public RestClient goldPriceRestClient(CloseableHttpClient httpClient){
        return createRestClient(httpClient, externalApisProperties.getMetalService(), "MetalApiService");
    }

    private RestClient createRestClient(CloseableHttpClient httpClient,
                                        ExternalApisProperties.ApiConfig config,
                                        String serviceName) {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        // Use service-specific timeout if provided, otherwise use default
        int connectionTimeout =  httpClientProperties.getConnectionTimeout();

        factory.setConnectTimeout(connectionTimeout);

        log.info("Creating RestClient for {} with baseUrl: {}", serviceName, config.getBaseUrl());

        return RestClient.builder()
                .baseUrl(config.getBaseUrl())
                .requestFactory(factory)
                .requestInterceptor((request, body, execution) -> {
                    log.debug("{} Request: {} {}", serviceName, request.getMethod(), request.getURI());
                    return execution.execute(request, body);
                })
                .build();
    }
}