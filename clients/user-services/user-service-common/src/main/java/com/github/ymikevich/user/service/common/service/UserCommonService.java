package com.github.ymikevich.user.service.common.service;

import com.github.ymikevich.user.service.common.model.Account;
import com.github.ymikevich.user.service.common.model.Country;
import com.github.ymikevich.user.service.common.model.Role;
import com.github.ymikevich.user.service.common.model.User;
import com.github.ymikevich.user.service.common.model.Visa;

import java.util.List;

public interface UserCommonService {

    List<Account> findAllAccountsByUserId(Long id);

    List<User> findAllIllegalsFromCountry(Country country);

    List<User> findUsersByRoleAndCountryInPassport(Role role, Country country);

    void linkUserWIthTwitterAccount(User user, Account account);

    void unlinkUserFromTwitterAccount(User user, Account account);

    void createAccountWithId(Long id);

    void updateAccountById(Long id);

    void deleteCountById(Long id);

    boolean deleteVisaFromUserPassportWhichLivesInAnotherCountry(User user, Visa visa);
}
