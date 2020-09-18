package com.betting.store.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class SwaggerConfig {

    private final SwaggerProperties properties;

    @Bean
    public Docket api(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo);
    }

    @Bean
    public ApiInfo apiInfo() {
        SwaggerProperties.Contact contact = properties.getContact();
        List<SwaggerProperties.VendorExtension> vendorExtensions = properties.getVendorExtensions();

        return new ApiInfo(
                properties.getTitle(),
                properties.getDescription(),
                properties.getVersion(),
                properties.getTermsOfServiceUrl(),
                new Contact(contact.getName(), contact.getUrl(), contact.getEmail()),
                properties.getLicense(),
                properties.getLicenseUrl(),
                vendorExtensions.stream()
                        .map(extension -> new StringVendorExtension(extension.getName(), extension.getValue()))
                        .collect(Collectors.toList()));
    }

}
