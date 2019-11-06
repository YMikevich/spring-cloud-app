package com.github.ymikevich.spring.data.jpa.user.service.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {

    private Integer id;
    private String nickname;
    private String email;
}
