package com.tuncode.microservices.currencyexchangeservice;

import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
    private static final String CIRCUIT_BREAKER_INSTANCE = "sampleApi";

    @GetMapping("/sample-api")
//    @Retry(name = CIRCUIT_BREAKER_INSTANCE, fallbackMethod = "hardcodedResponse")
    @CircuitBreaker(name = CIRCUIT_BREAKER_INSTANCE, fallbackMethod = "hardcodedResponse")
    public String sampleApi() {
        logger.info("Sample Api call received");

        ResponseEntity<String> forEntity =
                new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);

        return forEntity.getBody();
    }

    public String hardcodedResponse(Exception e) {
        return "The service is down ! " + e.getMessage();
    }


}
