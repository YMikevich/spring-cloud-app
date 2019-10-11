package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TweetService {

    Page<Tweet> searchForTweets(SearchRequest searchRequest);

    void persistTweets(List<Tweet> tweets);
}
