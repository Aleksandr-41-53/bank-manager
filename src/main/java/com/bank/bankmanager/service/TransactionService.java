package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.repos.TransactionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;

    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public List<Transaction> getAll() {
        return transactionRepo.findAll();
    }
}
