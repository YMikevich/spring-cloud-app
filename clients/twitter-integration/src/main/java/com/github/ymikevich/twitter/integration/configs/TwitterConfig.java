package com.github.ymikevich.twitter.integration.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitter SDK config.
 */
@Configuration
public class TwitterConfig {

    private final String consumerKey;
    private final String consumerSecret;
    private final String accessToken;
    private final String accessTokenSecret;

    /**
     * Instantiates a new Twitter config.
     *
     * @param consumerKey       the consumer key
     * @param consumerSecret    the consumer secret
     * @param accessToken       the access token
     * @param accessTokenSecret the access token secret
     */
    public TwitterConfig(@Value("${twitter.oauth.consumerKey}") final String consumerKey,
                         @Value("${twitter.oauth.consumerSecret}") final String consumerSecret,
                         @Value("${twitter.oauth.accessToken}") final String accessToken,
                         @Value("${twitter.oauth.accessTokenSecret}") final String accessTokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    /**
     * Twitter twitter.
     *
     * @return the twitter
     */
    @Bean
    public Twitter twitter() {
        var configurationBuilder = new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        return new TwitterFactory(configurationBuilder.build()).getInstance();
    }
}
