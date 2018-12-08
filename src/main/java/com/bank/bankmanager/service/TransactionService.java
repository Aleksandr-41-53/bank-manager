package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.TransactionType;
import com.bank.bankmanager.repos.TransactionRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final InvoiceService invoiceService;

    public TransactionService(TransactionRepo transactionRepo, InvoiceService invoiceService) {
        this.transactionRepo = transactionRepo;
        this.invoiceService = invoiceService;
    }

    public List<Transaction> getAll() {
        return transactionRepo.findAll();
    }

    public boolean add(BigDecimal cash, Invoice sender, Invoice recipient) {
        Transaction transaction = new Transaction();

        MathContext mc = new MathContext(2);
        BigDecimal tmp = sender.getCash().subtract(cash, mc);
        if (tmp.compareTo(BigDecimal.ZERO) < 0) return false;

        transaction.setCash(cash);
        transaction.setSenderCash(sender.getCash());
        transaction.setRecipientCash(recipient.getCash());
        transaction.setInvoiceSender(sender);
        transaction.setInvoiceRecipient(recipient);
        transaction.setTstz(Calendar.getInstance().getTime());

        sender.setCash(tmp);
        recipient.setCash(recipient.getCash().add(cash));

        transactionRepo.save(transaction);
        invoiceService.save(sender);
        invoiceService.save(recipient);

        return true;
    }
}
