package com.github.ymikevich.user.service.common.service;

import com.github.ymikevich.user.service.common.model.Account;
import com.github.ymikevich.user.service.common.model.Role;
import com.github.ymikevich.user.service.common.model.User;

import java.util.List;

public interface UserService {

    List<Account> findAllAccountsByUserId(Long id);

    List<User> findAllIllegalsFromCountry(String countryName);

    List<User> findUsersByRoleAndCountryInPassport(Role role, Long countryId);

    void linkUserWithTwitterAccount(Long userId, Long accountId);

    void unlinkUserFromTwitterAccount(Long userId, Long accountId);

    Account saveAccount(Account account);

    Account updateAccountById(Long id, Account account);

    void deleteAccountById(Long id);

    void deleteVisaFromUserPassportWhichLivesInAnotherCountry(Long userId, Long visaId);
}
