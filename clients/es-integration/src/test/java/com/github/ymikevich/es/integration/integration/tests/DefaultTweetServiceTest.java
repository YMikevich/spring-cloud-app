package com.github.ymikevich.es.integration.integration.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import com.github.ymikevich.es.integration.api.requests.statistics.StatisticsRequest;
import com.github.ymikevich.es.integration.exceptions.InvalidStatisticsRequestException;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import com.github.ymikevich.es.integration.services.DefaultTweetService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.List;

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
    public void getUserStatistics_whenResponseIsEmpty_thenThrowInvalidStatisticsRequestException() {
        //given
        var statisticsRequest = new StatisticsRequest();
        statisticsRequest.setUsername("Jack");
        statisticsRequest.setSinceDays(10);

        var searchResponse = mock(SearchResponse.class);

        when(searchResponse.getHits()).thenReturn(SearchHits.empty());
        when(elasticsearchTemplate.query(any(), any())).thenReturn(searchResponse);

        //when
        tweetService.getUserStatistics(statisticsRequest);
        //then throw InvalidStatisticsRequestException
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
