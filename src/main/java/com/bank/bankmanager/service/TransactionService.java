package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.TransactionType;
import com.bank.bankmanager.repos.TransactionRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public void add(BigDecimal cash, Invoice sender, Invoice recipient) {
        Transaction credits = new Transaction();
        Transaction debits = new Transaction();

        credits.setType(TransactionType.CREDITS);
        debits.setType(TransactionType.DEBITS);
        credits.setCash(cash);
        debits.setCash(cash);
        credits.setInvoiceCash(sender.getCash().subtract(cash));
        debits.setInvoiceCash(sender.getCash().add(cash));

        credits.setInvoiceSender(sender);
        credits.setInvoiceRecipient(recipient);
        debits.setInvoiceSender(recipient);
        debits.setInvoiceRecipient(sender);

        sender.setCash(credits.getCash());
        recipient.setCash(debits.getCash());

        invoiceService.save(sender);
        transactionRepo.save(credits);

        invoiceService.save(recipient);
        transactionRepo.save(debits);
    }
}
