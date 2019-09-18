package com.github.ymikevich.twitter.integration.responses;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Twitter user.
 */
@Getter
@Setter
public class TwitterUser {
    private Long id;
    private String email;
    private String name;
}
