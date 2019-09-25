package com.github.ymikevich.es.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Elastic Search integration application.
 */
@SpringBootApplication
public class EsIntegrationApplication {

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        SpringApplication.run(EsIntegrationApplication.class, args);
    }

}
