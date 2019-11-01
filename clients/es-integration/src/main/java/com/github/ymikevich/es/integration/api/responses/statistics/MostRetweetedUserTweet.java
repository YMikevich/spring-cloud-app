package com.github.ymikevich.es.integration.api.responses.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MostRetweetedUserTweet {

    private Integer retweetValue;
    private String tweetId;
    private String source;
}
