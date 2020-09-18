package com.betting.store.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "product-api")
public class ProductApiProperties {

    private String baseUrl;

}
