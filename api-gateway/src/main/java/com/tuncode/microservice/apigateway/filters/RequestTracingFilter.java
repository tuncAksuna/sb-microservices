package com.tuncode.microservice.apigateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * This filter responsible to generate a trace ID or correlation ID whenever a new request
 * came to our Gateway Server from the external client applications(s)
 */
@Order(1)
@Component
public class RequestTracingFilter implements GlobalFilter {

    private static final Logger logger = Logger.getLogger(RequestTracingFilter.class.getName());

    private final FilterUtility filterUtility;

    public RequestTracingFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        if (isCorrelationIdPresent(requestHeaders)) {
            logger.info("gateway-correlation-id found in RequestTraceFilter: " +
                    filterUtility.getCorrelationId(requestHeaders));
        } else {
            String correlationID = UUID.randomUUID().toString();
            exchange = filterUtility.setCorrelationId(exchange, correlationID);
            logger.info("gateway-correlation-id generated in RequestTraceFilter: " + correlationID);
        }

        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return filterUtility.getCorrelationId(requestHeaders) != null;
    }

}
