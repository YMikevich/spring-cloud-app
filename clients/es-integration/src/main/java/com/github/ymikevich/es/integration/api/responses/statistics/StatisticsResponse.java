package com.github.ymikevich.es.integration.api.responses.statistics;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ymikevich.es.integration.configs.deserializers.StatisticsResponseDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonDeserialize(using = StatisticsResponseDeserializer.class)
public class StatisticsResponse {

    private Double averageLikeValue;
    private MostRetweetedUserTweet mostRetweetedUserTweet;
}
