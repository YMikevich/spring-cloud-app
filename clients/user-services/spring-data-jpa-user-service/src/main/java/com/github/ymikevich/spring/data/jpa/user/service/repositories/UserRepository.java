package com.github.ymikevich.spring.data.jpa.user.service.repositories;

import com.github.ymikevich.spring.data.jpa.user.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
