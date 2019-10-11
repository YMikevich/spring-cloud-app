package com.github.ymikevich.es.integration.controllers;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import com.github.ymikevich.es.integration.api.responses.search.SearchResponse;
import com.github.ymikevich.es.integration.converters.PageToSearchResponseConverter;
import com.github.ymikevich.es.integration.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("tweets")
@RequiredArgsConstructor
public class SearchController {

    private final TweetService tweetService;
    private final PageToSearchResponseConverter pageToSearchResponseConverter;

    @PostMapping("search")
    public ResponseEntity<SearchResponse<Tweet>> search(@Valid @RequestBody final SearchRequest searchRequest) {
        var tweets = pageToSearchResponseConverter.convert(tweetService.searchForTweets(searchRequest));
        return ResponseEntity.ok().body(tweets);
    }
}
