package com.github.ymikevich.spring.data.jpa.user.service.repositories;

import com.github.ymikevich.spring.data.jpa.user.service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
