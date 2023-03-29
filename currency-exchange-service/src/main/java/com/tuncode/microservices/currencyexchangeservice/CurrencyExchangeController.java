package com.tuncode.microservices.currencyexchangeservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyExchangeController {

    private final CurrencyExchangeRepository repository;
    private final Environment environment;
    private static final String CURRENCY_EXCHANGE_INSTANCE = "currencyExchangeService";

    @Autowired
    public CurrencyExchangeController(CurrencyExchangeRepository repository, Environment environment) {
        this.repository = repository;
        this.environment = environment;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
//    @CircuitBreaker(name = CURRENCY_EXCHANGE_INSTANCE, fallbackMethod = "exchangeServiceFallBack")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);

        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data !");
        }

        // we will know which instance of the currency exchange service is actually responding back !
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);

        return currencyExchange;
    }

//    public ResponseEntity<String> exchangeServiceFallBack(Exception exc) {
//        return new ResponseEntity<String> ("Currency Exchange service is down ", HttpStatus.OK);
//    }
}
