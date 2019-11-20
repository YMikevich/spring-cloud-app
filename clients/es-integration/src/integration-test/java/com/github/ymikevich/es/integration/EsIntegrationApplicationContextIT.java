package com.github.ymikevich.es.integration;

import com.github.ymikevich.es.integration.configs.RabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.ymikevich.es.integration"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RabbitConfig.class)})
public class EsIntegrationApplicationContextIT {

    public static void main(final String[] args) {
        SpringApplication.run(EsIntegrationApplicationContextIT.class, args);
    }
}
