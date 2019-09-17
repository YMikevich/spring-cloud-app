package com.github.ymikevich.twitter.integration.controllers;

import com.github.ymikevich.twitter.integration.services.TweetSearchEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;

import java.util.List;

/**
 * The type Twitter integration controller.
 */
@RestController
@RequiredArgsConstructor
public class TwitterIntegrationController {

    private final TweetSearchEngine tweetSearchEngine;

    /**
     * Gets tweets by username from service.
     *
     * @return the tweets by username
     */
    @GetMapping
    public List<Status> getTweetsByUsername() {
        //todo implementation



        return null;
    }
}
