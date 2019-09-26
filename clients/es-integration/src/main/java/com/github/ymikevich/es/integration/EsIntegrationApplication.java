package com.github.ymikevich.es.integration;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Elastic Search integration application.
 */
@SpringBootApplication
@EnableRabbit
public class EsIntegrationApplication {
    public static void main(final String[] args) {
        SpringApplication.run(EsIntegrationApplication.class, args);
    }
}
