package com.github.ymikevich.es.integration.configs.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.ymikevich.es.integration.api.responses.statistics.MostRetweetedUserTweet;
import com.github.ymikevich.es.integration.api.responses.statistics.StatisticsResponse;

import java.io.IOException;

public class StatisticsResponseDeserializer extends StdDeserializer<StatisticsResponse> {

    private static final int TWEET_WITH_MAX_LIKES_INDEX = 0;

    public StatisticsResponseDeserializer() {
        this(null);
    }

    public StatisticsResponseDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public StatisticsResponse deserialize(final JsonParser jp, final DeserializationContext ctxt)
            throws IOException {

        JsonNode productNode = jp.getCodec().readTree(jp);

        return StatisticsResponse.builder()
                .mostRetweetedUserTweet(MostRetweetedUserTweet.builder()
                        .retweetValue(productNode.get("hits").get("hits").get(TWEET_WITH_MAX_LIKES_INDEX).get("_source")
                                .get("favoriteCount").asInt())
                        .source(productNode.get("hits").get("hits").get(TWEET_WITH_MAX_LIKES_INDEX).get("_source")
                                .get("source").asText())
                        .tweetId(productNode.get("hits").get("hits").get(TWEET_WITH_MAX_LIKES_INDEX).get("_source")
                                .get("tweetId").asText())
                        .build())
                .averageLikeValue(productNode.get("aggregations").get("avg_favorite_count").get("value").doubleValue())
                .build();
    }
}
