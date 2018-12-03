package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User       findByUsername(String name);
    List<User> findByActive(boolean active);

}
