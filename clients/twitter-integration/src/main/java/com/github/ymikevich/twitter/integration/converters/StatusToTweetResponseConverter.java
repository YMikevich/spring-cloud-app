package com.github.ymikevich.twitter.integration.converters;

import com.github.ymikevich.twitter.integration.responses.TweetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import twitter4j.Status;

/**
 * The type Status to TweetResponse converter.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class StatusToTweetResponseConverter implements Converter<Status, TweetResponse> {

    private final DateToLocalDateTimeConverter dateToLocalDateTimeConverter;
    private final UserToTwitterUserConverter userToTwitterUserConverter;

    @Override
    public TweetResponse convert(final Status status) {
        log.trace("Converting Status to TweetResponse");

        if (status == null) {
            log.warn("Status equals null while converting");
            return null;
        }

        log.trace("Converted successfully");
        return TweetResponse.builder()
                .id(status.getId())
                .createdAt(dateToLocalDateTimeConverter.convert(status.getCreatedAt()))
                .text(status.getText())
                .source(status.getSource())
                .favoriteCount(status.getFavoriteCount())
                .retweetCount(status.getRetweetCount())
                .user(userToTwitterUserConverter.convert(status.getUser()))
                .build();
    }
}
