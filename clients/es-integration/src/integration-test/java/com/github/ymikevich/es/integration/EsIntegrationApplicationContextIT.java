package com.github.ymikevich.es.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

@SpringBootApplication
public class EsIntegrationApplicationContextIT {

    public static void main(final String[] args) {
        SpringApplication.run(EsIntegrationApplicationContextIT.class, args);
    }
}
