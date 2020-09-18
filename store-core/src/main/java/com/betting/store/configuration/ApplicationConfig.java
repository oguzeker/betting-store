package com.betting.store.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.concurrent.TimeUnit;


@Configuration
@AllArgsConstructor
public class ApplicationConfig {

    private static final String UTF_8 = "UTF-8";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    private final ConnectionProperties connectionProperties;

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding(UTF_8);
        filter.setForceEncoding(Boolean.TRUE);
        return filter;
    }

    @Bean
    public RestTemplate restTemplate(CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .modules(new JavaTimeModule(), new SimpleModule())
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();


        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Bean
    public CloseableHttpClient httpClient(RequestConfig requestConfig,
                                          PoolingHttpClientConnectionManager pool) {
        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .disableAutomaticRetries()
                .setConnectionTimeToLive(connectionProperties.getTimeToLive(), TimeUnit.MINUTES)
                .setConnectionManager(pool)
                .build();
    }

    @Bean
    public SocketConfig socketConfig() {
        return SocketConfig.custom()
                .setTcpNoDelay(Boolean.TRUE)
                .setSoTimeout(connectionProperties.getTimeout())
                .setSoKeepAlive(Boolean.TRUE)
                .setSoReuseAddress(Boolean.TRUE)
                .build();
    }

    @Bean
    public Registry<ConnectionSocketFactory> connectionSocketFactoryRegistry() {
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HTTP, PlainConnectionSocketFactory.getSocketFactory())
                .register(HTTPS, SSLConnectionSocketFactory.getSocketFactory())
                .build();
    }
    
    @Bean
    public RequestConfig requestConfig() {
        int timeout = connectionProperties.getTimeout();

        return RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setContentCompressionEnabled(Boolean.TRUE)
                .setMaxRedirects(connectionProperties.getMaxRedirectionCount())
                .setAuthenticationEnabled(Boolean.FALSE)
                .setExpectContinueEnabled(Boolean.FALSE)
                .setRedirectsEnabled(Boolean.FALSE)
                .setRelativeRedirectsAllowed(Boolean.FALSE).build();
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(
            SocketConfig socketConfig, Registry<ConnectionSocketFactory> registry) {
        int maxConnectionCount = connectionProperties.getMaxConnectionCount();

        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(registry);
        pool.setDefaultMaxPerRoute(maxConnectionCount);
        pool.setMaxTotal(maxConnectionCount);
        pool.setDefaultSocketConfig(socketConfig);
        return pool;
    }

}