package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.SearchRequest;

import java.util.List;

public interface TweetService {
    List<Tweet> searchForTweets(SearchRequest searchRequest);
    void persistTweets(List<Tweet> tweets);
}
