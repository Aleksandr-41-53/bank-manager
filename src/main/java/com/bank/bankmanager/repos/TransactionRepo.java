package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
