package com.github.ymikevich.twitter.integration.converters;

import com.github.ymikevich.twitter.integration.api.model.TwitterUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import twitter4j.User;

/**
 * The type User to TwitterUser converter.
 */
@Component
@Slf4j
public class UserToTwitterUserConverter implements Converter<User, TwitterUser> {

    @Override
    public TwitterUser convert(final User user) {
        log.trace("Converting User to TwitterUser");

        if (user == null) {
            log.warn("User equals null while converting");
            return null;
        }

        log.trace("Converted successfully");
        return TwitterUser.builder()
                .id(user.getId())
                .name(user.getScreenName())
                .email(user.getEmail())
                .build();
    }
}
