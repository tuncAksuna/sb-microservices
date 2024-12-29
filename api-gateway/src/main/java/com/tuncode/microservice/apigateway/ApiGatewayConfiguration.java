package com.tuncode.microservice.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    /**
     * Customizing the API Gateway Routing
     */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(p -> p
                        .path("/api/v1/exchange/**") // not dynamic - no variables are captured for later use - for capturing variables --> currency/exchange/{segment}
                        .filters(filter -> filter
                                // example request: http://host:port:8765/api/v1/exchange/from/TR/to/USD
                                .rewritePath("api/v1/exchange/(?<segment>.*)", "/currency-exchange/${segment}"))
                        .uri("lb://CURRENCY-EXCHANGE")) // for load balancing and eureka server

                .route(p -> p
                        .path("/api/v1/conversion/**")
                        .filters(filter -> filter
                                // example request http://host:port:8765/api/v1/conversion/from/tr/to/usd/quantity/10
                                .rewritePath("/api/v1/conversion/(?<segment>.*)", "/currency-conversion/${segment}"))
                        .uri("lb://CURRENCY-CONVERSION"))
                .build();
    }
}
