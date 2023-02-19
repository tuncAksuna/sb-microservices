package com.tuncode.microservices.limitservice.controller;

import com.tuncode.microservices.limitservice.bean.Limits;
import com.tuncode.microservices.limitservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration limitsConfiguration;


    @GetMapping("/limits")
    public Limits retrieveLimits() {
        return new Limits(limitsConfiguration.getMinimum(), limitsConfiguration.getMaximum());
    }

}
