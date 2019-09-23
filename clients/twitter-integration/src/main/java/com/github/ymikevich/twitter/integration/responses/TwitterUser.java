package com.github.ymikevich.twitter.integration.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The type Twitter user.
 */
@Getter
@Setter
@Builder
public class TwitterUser implements Serializable {
    private Long id;
    private String email;
    private String name;
}
