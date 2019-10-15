package com.github.ymikevich.es.integration.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "tweets", type = "tweet")
@Getter
@Setter
public class Tweet {

    @Id
    private Long tweetId;
    private LocalDateTime createdAt;
    private String text;
    private String source;
    private int favoriteCount;
    private int retweetCount;
    private TwitterUser user;
}
