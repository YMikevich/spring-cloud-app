package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import com.github.ymikevich.es.integration.api.requests.statistics.StatisticsRequest;
import com.github.ymikevich.es.integration.api.responses.statistics.StatisticsResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface TweetService {

    Page<Tweet> searchForTweets(SearchRequest searchRequest);

    void persistTweets(List<Tweet> tweets);

    StatisticsResponse getUserStatistics(StatisticsRequest statisticsRequest) throws IOException;
}
