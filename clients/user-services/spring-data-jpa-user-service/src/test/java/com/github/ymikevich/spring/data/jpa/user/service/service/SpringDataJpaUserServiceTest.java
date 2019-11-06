package com.github.ymikevich.spring.data.jpa.user.service.service;

import com.github.ymikevich.spring.data.jpa.user.service.model.User;
import com.github.ymikevich.spring.data.jpa.user.service.repositories.AccountRepository;
import com.github.ymikevich.spring.data.jpa.user.service.repositories.UserRepository;
import com.github.ymikevich.user.service.common.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataJpaUserServiceTest {

    @Autowired
    SpringDataJpaUserService service;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void saveAccount() {
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
    public void updateAccountById() {
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
    public void deleteAccountById() {
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

    @Test
    public void findAllAccountsByUserId() {
        //given
        var account1 = new Account();
        account1.setId(1L);
        account1.setEmail("hello@mail.com");
        account1.setNickname("Bred");
        account1.setUsers(List.of());

        var account2 = new Account();
        account2.setId(2L);
        account2.setEmail("he@mail.com");
        account2.setNickname("Jack");
        account2.setUsers(List.of());
        service.saveAccount(account1);
        service.saveAccount(account2);


        var accounts = new ArrayList<Account>();
        accounts.add(account1);
        accounts.add(account2);

        var user = new User();
        user.setId(1);
        user.setAccounts(accounts.stream()
                .map(account -> modelMapper.map(account, com.github.ymikevich.spring.data.jpa.user.service.model.Account.class))
                .collect(Collectors.toList()));

        userRepository.save(user);

        //when
        var userAccounts = service.findAllAccountsByUserId(1L);

        //then
        assertEquals(userAccounts.size(), accounts.size());
    }
}