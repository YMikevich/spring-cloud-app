package com.github.ymikevich.twitter.integration.controllers;

import com.github.ymikevich.twitter.integration.services.TweetSearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;

import java.util.List;

@RestController
public class TwitterIntegrationController {

    private final TweetSearchEngine tweetSearchEngine;

    @Autowired
    public TwitterIntegrationController(final TweetSearchEngine tweetSearchEngine) {
        this.tweetSearchEngine = tweetSearchEngine;
    }

    @GetMapping
    public List<Status> getTweetsByUsername() {
        //todo implementation

        return null;
    }
}
