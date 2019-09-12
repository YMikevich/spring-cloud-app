package com.github.ymikevich.twitter.integration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.List;

@Service
public class TweetSearchEngineTwitter4j implements TweetSearchEngine {

    private final Twitter twitter;

    @Autowired
    public TweetSearchEngineTwitter4j(final Twitter twitter) {
        this.twitter = twitter;
    }

    @Override
    public List<Status> findTweetsByUsername(final String username) {
        //todo implementation
        return null;
    }
}
