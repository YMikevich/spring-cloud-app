package com.github.ymikevich.es.integration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import com.github.ymikevich.es.integration.api.requests.statistics.StatisticsRequest;
import com.github.ymikevich.es.integration.converters.SearchRequestToPageableConverter;
import com.github.ymikevich.es.integration.exceptions.InvalidStatisticsRequestException;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchResponseSections;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.profile.SearchProfileShardResults;
import org.elasticsearch.search.suggest.Suggest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private ElasticsearchTemplate elasticsearchTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private Converter<SearchRequest, Pageable> searchRequestToPageableConverter;
    @InjectMocks
    private DefaultTweetService tweetService;

    @Test
    public void persistTweets() {
        //given
        var tweet = new Tweet();
        tweet.setTweetId(1L);
        var tweets = List.of(tweet);

        //when
        tweetService.persistTweets(tweets);

        //then
        verify(tweetRepository).saveAll(tweets);
    }

    @Test(expected = InvalidStatisticsRequestException.class)
    public void getUserStatisticsInvalidStatisticsRequestExceptionCheck() {
        //given
        var statisticsRequest = new StatisticsRequest();
        statisticsRequest.setUsername("Jack");
        statisticsRequest.setSinceDays(10);

        var suggetion = new Suggest.Suggestion<Suggest.Suggestion.Entry<Suggest.Suggestion.Entry.Option>>("", 0);

        var suggetions = new ArrayList<Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>>>();
        suggetions.add(suggetion);

        var searchResponseSections = new SearchResponseSections(new SearchHits(new SearchHit[0],
                0, 0), new Aggregations(List.of()), new Suggest(suggetions), false,
                false, new SearchProfileShardResults(Map.of()), 0);

        var searchResponse = mock(SearchResponse.class);
        var aggregations = new Aggregations(List.of());

        when(searchResponse.getHits()).thenReturn(SearchHits.empty());
        when(searchResponse.getAggregations()).thenReturn(aggregations);

        when(elasticsearchTemplate.query(any(), any()))
                .thenReturn(searchResponse);

        //when
        //then throw InvalidStatisticsRequestException
        tweetService.getUserStatistics(statisticsRequest);
    }

    @Test
    public void searchForTweets() {
        //given
        var searchRequest = new SearchRequest();
        searchRequest.setSearchString("hello");

        //when
        tweetService.searchForTweets(searchRequest);

        //then
        verify(tweetRepository, times(1)).findAllByTextLike(anyString(), any());
        verify(searchRequestToPageableConverter, times(1)).convert(searchRequest);
    }
}