package com.github.ymikevich.es.integration;

import com.github.ymikevich.es.integration.configs.RabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;

@MockBean(RabbitConfig.class)
@SpringBootApplication
public class EsIntegrationApplicationContextIT {

    public static void main(final String[] args) {
        SpringApplication.run(EsIntegrationApplicationContextIT.class, args);
    }
}
