package com.github.ymikevich.spring.data.jpa.user.service.service;

import com.github.ymikevich.user.service.common.model.Account;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = "com.github.ymikevich.spring.data.jpa.user.service")
class SpringDataJpaUserServiceTest {

    SpringDataJpaUserService service;

    @Autowired
    public SpringDataJpaUserServiceTest(SpringDataJpaUserService service) {
        this.service = service;
    }

    @Before
    public void setUp() throws Exception {
    }

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
    }

    @Test
    void deleteAccountById() {
    }
}