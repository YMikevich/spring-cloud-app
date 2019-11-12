package com.github.ymikevich.twitter.integration.feign.clients;

import com.github.ymikevich.twitter.integration.api.model.responses.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "spring-data-jpa-user-service")
public interface UserClient {

    @GetMapping("accounts/users/{id}")
    List<AccountResponse> getAccountsByUserId(@PathVariable("id") Long userId);
}
