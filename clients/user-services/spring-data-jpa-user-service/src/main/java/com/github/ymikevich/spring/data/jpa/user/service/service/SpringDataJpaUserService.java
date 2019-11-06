package com.github.ymikevich.spring.data.jpa.user.service.service;

import com.github.ymikevich.spring.data.jpa.user.service.model.Account;
import com.github.ymikevich.spring.data.jpa.user.service.repositories.AccountRepository;
import com.github.ymikevich.spring.data.jpa.user.service.repositories.UserRepository;
import com.github.ymikevich.user.service.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpringDataJpaUserService implements UserService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<com.github.ymikevich.user.service.common.model.Account> findAllAccountsByUserId(Long id) {
        return userRepository.findById(id.intValue())
                .map(user -> user.getAccounts())
                .map(this::toCommonModel)
                .orElse(List.of());
    }

    @Override
    public List<com.github.ymikevich.user.service.common.model.User> findAllIllegalsFromCountry(String countryName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<com.github.ymikevich.user.service.common.model.User> findUsersByRoleAndCountryInPassport(com.github.ymikevich.user.service.common.model.Role role, Long countryId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void linkUserWithTwitterAccount(Long userId, Long accountId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unlinkUserFromTwitterAccount(Long userId, Long accountId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public com.github.ymikevich.user.service.common.model.Account saveAccount(com.github.ymikevich.user.service.common.model.Account account) {
        return modelMapper.map(accountRepository.save(modelMapper.map(account, Account.class)),
                com.github.ymikevich.user.service.common.model.Account.class);
    }

    @Override
    public com.github.ymikevich.user.service.common.model.Account updateAccountById(Long id, com.github.ymikevich.user.service.common.model.Account account) {
        return modelMapper.map(accountRepository.save(modelMapper.map(account, Account.class)),
                com.github.ymikevich.user.service.common.model.Account.class);
    }

    @Override
    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id.intValue());
    }

    @Override
    public void deleteVisaFromUserPassportWhichLivesInAnotherCountry(Long userId, Long visaId) {
        throw new UnsupportedOperationException();
    }

    private List<com.github.ymikevich.user.service.common.model.Account> toCommonModel(List<Account> accounts) {
        return accounts.stream()
                .map(account -> modelMapper.map(account, com.github.ymikevich.user.service.common.model.Account.class))
                .collect(Collectors.toList());
    }
}
