package com.betting.store;

import com.betting.store.configuration.ConnectionProperties;
import com.betting.store.configuration.ProductApiProperties;
import com.betting.store.configuration.SwaggerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin
@EnableSwagger2
@EnableDiscoveryClient
@EnableMongoRepositories
@SpringBootApplication
@EnableConfigurationProperties({
		ConnectionProperties.class,
		ProductApiProperties.class,
		SwaggerProperties.class
})
public class StoreCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreCoreApplication.class, args);
	}

}
