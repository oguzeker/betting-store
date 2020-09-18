package com.betting.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaDiscovery {
    public static void main(String[] args) {
        SpringApplication.run(EurekaDiscovery.class, args);
    }
}