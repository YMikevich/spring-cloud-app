package com.github.ymikevich.es.integration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import com.github.ymikevich.es.integration.api.requests.statistics.StatisticsRequest;
import com.github.ymikevich.es.integration.api.responses.statistics.StatisticsResponse;
import com.github.ymikevich.es.integration.configs.deserializers.StatisticsResponseDeserializer;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
@RequiredArgsConstructor
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
    public StatisticsResponse getUserStatistics(final StatisticsRequest statisticsRequest) throws IOException {

        var fromDate = LocalDateTime.now().minusDays(statisticsRequest.getSinceDays())
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        SearchQuery query = new NativeSearchQueryBuilder()
                .withIndices("tweets")
                .withTypes("tweet")
                .withQuery(boolQuery().must(termQuery("user.name", statisticsRequest.getUsername()))
                        .filter(QueryBuilders.rangeQuery("createdAt").gte(fromDate)))
                .addAggregation(AggregationBuilders.avg(AVG_AGGREGATION_NAME).field("favoriteCount"))
                .withSort(SortBuilders.fieldSort("retweetCount").order(SortOrder.DESC))
                .build();

        var searchResponse = elasticsearchTemplate.query(query, response -> response);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(StatisticsResponse.class, new StatisticsResponseDeserializer());
        objectMapper.registerModule(module);

        return objectMapper.readValue(searchResponse.toString(), StatisticsResponse.class);
    }

    @Override
    public Page<Tweet> searchForTweets(final SearchRequest searchRequest) {
        return tweetRepository.findAllByTextLike(searchRequest.getSearchString(),
                searchRequestToPageableConverter.convert(searchRequest));
    }
}
