package com.tuncode.microservice.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    /**
     * Customizing the API Gateway Routing
     */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange")) // TODO : For load balancing and we want to talk to Eureka Server to find the location of this service

                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))

                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))

                .build();
    }
}
