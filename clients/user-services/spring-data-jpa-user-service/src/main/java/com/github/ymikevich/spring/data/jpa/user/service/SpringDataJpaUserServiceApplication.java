package com.github.ymikevich.spring.data.jpa.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringDataJpaUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaUserServiceApplication.class, args);
    }

}
