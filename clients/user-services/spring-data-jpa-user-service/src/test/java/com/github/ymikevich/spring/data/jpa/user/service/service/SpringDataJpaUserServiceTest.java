package com.github.ymikevich.spring.data.jpa.user.service.service;

import com.github.ymikevich.spring.data.jpa.user.service.repositories.AccountRepository;
import com.github.ymikevich.user.service.common.model.Account;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.github.ymikevich.spring.data.jpa.user.service")
class SpringDataJpaUserServiceTest {

    @Autowired
    SpringDataJpaUserService service;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void saveAccount() {
        //given
        var accountToSave = new Account();
        accountToSave.setId(1L);
        accountToSave.setEmail("hello@mail.com");
        accountToSave.setNickname("Bred");
        accountToSave.setUsers(List.of());

        //when
        var savedAccount = service.saveAccount(accountToSave);

        assertNotNull(savedAccount);
        assertEquals(accountToSave.getId(), savedAccount.getId());
        assertEquals(accountToSave.getNickname(), savedAccount.getNickname());
        assertEquals(accountToSave.getEmail(), savedAccount.getEmail());
    }

    @Test
    void updateAccountById() {
        //given
        var account = new Account();
        account.setId(1L);
        account.setEmail("hello@mail.com");
        account.setNickname("Bred");
        account.setUsers(List.of());
        service.saveAccount(account);
        account.setNickname("Sanya");

        //when
        var updatedAccount = service.updateAccountById(1L, account);

        //then
        assertEquals(updatedAccount.getNickname(), "Sanya");
    }

    @Test
    void deleteAccountById() {
        //given
        var account = new Account();
        account.setId(1L);
        account.setEmail("hello@mail.com");
        account.setNickname("Bred");
        account.setUsers(List.of());
        var savedAccount = service.saveAccount(account);

        //when
        service.deleteAccountById(savedAccount.getId());
        assertTrue(accountRepository.findById(1).isEmpty());
    }
}