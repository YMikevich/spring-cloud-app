package com.github.ymikevich.es.integration.controllers;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.SearchRequest;
import com.github.ymikevich.es.integration.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("tweets")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService defaultSearchService;

    @PostMapping("search")
    public ResponseEntity<List<Tweet>> search(@Valid @RequestBody final SearchRequest searchRequest) {
        var tweets = defaultSearchService.searchForTweets(searchRequest);
        return ResponseEntity.ok().body(tweets);
    }
}
