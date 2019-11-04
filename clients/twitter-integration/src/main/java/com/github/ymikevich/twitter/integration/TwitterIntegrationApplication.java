package com.github.ymikevich.twitter.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * This class loads Spring Boot.
 */
@SpringBootApplication
@EnableEurekaClient
public class TwitterIntegrationApplication {

    /**
     * Main.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(TwitterIntegrationApplication.class, args);
    }
}
