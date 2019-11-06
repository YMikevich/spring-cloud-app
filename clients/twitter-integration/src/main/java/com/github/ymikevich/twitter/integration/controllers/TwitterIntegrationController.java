package com.github.ymikevich.twitter.integration.controllers;

import com.github.ymikevich.twitter.integration.services.TwitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Twitter integration controller.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class TwitterIntegrationController {

    private final TwitterService twitterService;

    @GetMapping("/sync/{userId}")
    public void sync(@PathVariable final Long userId) {
        twitterService.sync(userId);
    }
}
