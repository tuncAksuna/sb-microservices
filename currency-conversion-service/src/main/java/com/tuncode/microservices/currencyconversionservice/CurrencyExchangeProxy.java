package com.tuncode.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//@FeignClient(value = "currency-exchange", url = "localhost:8000")
@FeignClient(value = "currency-exchange") // Deleted url parameter for Netlix Eureka Automatic Load Balancing
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from,
                                             @PathVariable String to);

}
