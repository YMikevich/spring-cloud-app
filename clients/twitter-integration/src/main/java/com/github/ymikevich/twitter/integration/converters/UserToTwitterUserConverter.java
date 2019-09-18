package com.github.ymikevich.twitter.integration.converters;

import com.github.ymikevich.twitter.integration.responses.TwitterUser;
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
        log.info("Converting User to TwitterUser");

        if (user == null) {
            log.warn("User equals null while converting");
            return  null;
        }

        TwitterUser twitterUser = new TwitterUser();
        twitterUser.setId(user.getId());
        twitterUser.setEmail(user.getEmail());
        twitterUser.setName(user.getScreenName());

        log.info("Converted successfully");
        return twitterUser;
    }
}
