package com.github.ymikevich.twitter.integration.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Twitter SDK config.
 */
@Configuration
public class TwitterConfig {

    /**
     * Twitter twitter.
     *
     */
    @Bean
    public Twitter twitter() {
        return TwitterFactory.getSingleton();
    }


}
