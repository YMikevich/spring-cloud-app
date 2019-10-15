package com.github.ymikevich.es.integration.api.responses.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatisticsResponse {

    private Double averageLikeValue;
    private MostRetweetedUserTweet mostRetweetedUserTweet;
}
