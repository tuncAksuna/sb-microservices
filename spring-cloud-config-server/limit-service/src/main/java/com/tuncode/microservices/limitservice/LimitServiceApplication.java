package com.tuncode.microservices.limitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LimitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LimitServiceApplication.class, args);
    }

}
