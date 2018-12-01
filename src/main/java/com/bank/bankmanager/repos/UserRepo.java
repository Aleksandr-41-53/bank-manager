package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
