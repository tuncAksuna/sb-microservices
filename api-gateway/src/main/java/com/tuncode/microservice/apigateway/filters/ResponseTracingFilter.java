package com.tuncode.microservice.apigateway.filters;


import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

/**
 * The purpose of this filter is it is going to add the trace ID or correlation ID into the response
 * so that our client also is aware about the trace/correlation ID associated with a particular request
 */
@Configuration
public class ResponseTracingFilter {

    private static final Logger logger = Logger.getLogger(ResponseTracingFilter.class.getName());

    private final FilterUtility filterUtility;

    public ResponseTracingFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
            String correlationId = filterUtility.getCorrelationId(requestHeaders);

            logger.info("Updated the correlation id to the outbound headers: " + correlationId);
            exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
        }));
    }
}
