package com.tuncode.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    private final CurrencyExchangeProxy exchangeProxy;

    public CurrencyConversionController(CurrencyExchangeProxy exchangeProxy) {
        this.exchangeProxy = exchangeProxy;
    }

    // TODO : inter-service communication by FEIGN
    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
                                                               @PathVariable String to,
                                                               @PathVariable BigDecimal quantity) {


        CurrencyConversion currencyConversion = exchangeProxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(
                currencyConversion.getId(),
                from,
                to,
                quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment());
    }


    // TODO : inter-service communication by RestTemplate
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
                                                          @PathVariable String to,
                                                          @PathVariable BigDecimal quantity) {

        // using to make API calls (send request from CurrencyConversion to CurrencyExchange)
        HashMap<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from", from);
        uriVariable.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariable);

        return new CurrencyConversion(
                responseEntity.getBody().getId(),
                from,
                to,
                quantity,
                responseEntity.getBody().getConversionMultiple(),
                quantity.multiply(responseEntity.getBody().getConversionMultiple()),
                responseEntity.getBody().getEnvironment());
    }

}
