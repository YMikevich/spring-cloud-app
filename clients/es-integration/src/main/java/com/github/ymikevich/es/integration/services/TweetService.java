package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import com.github.ymikevich.es.integration.api.requests.statistics.StatisticsRequest;
import com.github.ymikevich.es.integration.api.responses.statistics.StatisticsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TweetService {

    Page<Tweet> searchForTweets(SearchRequest searchRequest);

    void persistTweets(List<Tweet> tweets);

    Optional<StatisticsResponse> getUserStatistics(StatisticsRequest statisticsRequest);
}
