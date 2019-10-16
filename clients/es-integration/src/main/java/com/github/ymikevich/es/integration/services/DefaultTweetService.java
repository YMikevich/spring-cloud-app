package com.github.ymikevich.es.integration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import com.github.ymikevich.es.integration.api.requests.statistics.StatisticsRequest;
import com.github.ymikevich.es.integration.api.responses.statistics.StatisticsResponse;
import com.github.ymikevich.es.integration.exceptions.InvalidStatisticsRequestException;
import com.github.ymikevich.es.integration.exceptions.JacksonConvertionException;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultTweetService implements TweetService {

    private static final String AVG_AGGREGATION_NAME = "avg_favorite_count";

    private final TweetRepository tweetRepository;
    private final Converter<SearchRequest, Pageable> searchRequestToPageableConverter;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void persistTweets(final List<Tweet> tweets) {
        tweetRepository.saveAll(tweets);
    }

    @Override
    public StatisticsResponse getUserStatistics(final StatisticsRequest statisticsRequest) {
        var fromDate = LocalDateTime.now().minusDays(statisticsRequest.getSinceDays())
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        var query = new NativeSearchQueryBuilder()
                .withIndices("tweets")
                .withTypes("tweet")
                .withQuery(boolQuery().must(termQuery("user.name", statisticsRequest.getUsername()))
                        .filter(QueryBuilders.rangeQuery("createdAt").gte(fromDate)))
                .addAggregation(AggregationBuilders.avg(AVG_AGGREGATION_NAME).field("favoriteCount"))
                .withSort(SortBuilders.fieldSort("retweetCount").order(SortOrder.DESC))
                .build();

        var searchResponse = elasticsearchTemplate.query(query, response -> response);

        if (searchResponse.getHits().totalHits == 0 || searchResponse.getAggregations().asList().size() == 0) {
            throw new InvalidStatisticsRequestException(statisticsRequest.getUsername(), fromDate);
        }

        var statisticsResponse = StatisticsResponse.builder().build();

        try {
            statisticsResponse = objectMapper.readValue(searchResponse.toString(), StatisticsResponse.class);
        } catch (IOException ex) {
            throw new JacksonConvertionException(ex);
        }

        return statisticsResponse;
    }

    @Override
    public Page<Tweet> searchForTweets(final SearchRequest searchRequest) {
        return tweetRepository.findAllByTextLike(searchRequest.getSearchString(),
                searchRequestToPageableConverter.convert(searchRequest));
    }
}
