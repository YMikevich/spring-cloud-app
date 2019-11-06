package com.github.ymikevich.spring.data.jpa.user.service.controllers;

import com.github.ymikevich.spring.data.jpa.user.service.responses.AccountResponse;
import com.github.ymikevich.spring.data.jpa.user.service.service.SpringDataJpaUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserServiceController {

    private final SpringDataJpaUserService service;
    private final ModelMapper modelMapper;

    @GetMapping("accounts/users/{id}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(@PathVariable("id") final Long userId) {

        var accounts = service.findAllAccountsByUserId(userId);
        return ResponseEntity.ok(accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList()));
    }
}
