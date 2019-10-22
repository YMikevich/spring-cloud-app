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

    boolean linkUserWIthTwitterAccount(User user, Account account);

    boolean unlinkUserFromTwitterAccount(User user, Account account);

    boolean createAccountWithId(Long id);

    boolean updateAccountById(Long id);

    boolean deleteCountById(Long id);

    boolean deleteVisaFromUserPassportWhichLivesInAnotherCountry(User user, Visa visa);
}
