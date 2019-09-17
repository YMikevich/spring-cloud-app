package com.github.ymikevich.twitter.integration.services;

import twitter4j.Status;

import java.util.List;

public interface TweetSearchEngine {

    List<Status> findTweetsByUsername(String username);
}
