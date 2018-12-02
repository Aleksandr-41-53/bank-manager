package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactioRepo extends JpaRepository<Transaction, Long> {
}
