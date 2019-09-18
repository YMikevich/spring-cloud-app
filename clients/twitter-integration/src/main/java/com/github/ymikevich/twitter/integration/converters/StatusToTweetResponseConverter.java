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
        log.info("Converting Status to TweetResponse");

        if (status == null) {
            log.warn("Status equals null while converting");
            return null;
        }

        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setId(status.getId());
        tweetResponse.setCreatedAt(dateToLocalDateTimeConverter.convert(status.getCreatedAt()));
        tweetResponse.setText(status.getText());
        tweetResponse.setSource(status.getSource());
        tweetResponse.setFavoriteCount(status.getFavoriteCount());
        tweetResponse.setRetweetCount((status.getRetweetCount()));
        tweetResponse.setUser(userToTwitterUserConverter.convert(status.getUser()));

        log.info("Converted successfully");
        return tweetResponse;
    }
}
