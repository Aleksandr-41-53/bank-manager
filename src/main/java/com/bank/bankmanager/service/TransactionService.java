package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.repos.TransactionRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
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

    public List<Transaction> getAllBySenderAndRecipient(Invoice invoice) {
        return transactionRepo.findAllByInvoiceSenderOrInvoiceRecipient(invoice, invoice);
    }

    public List<Transaction> getAllDebitsByInvoice(Invoice invoice) {
        return transactionRepo.findAllByInvoiceRecipient(invoice);
    }

    public List<Transaction> getAllCreditsByInvoice(Invoice invoice) {
        return transactionRepo.findAllByInvoiceSender(invoice);
    }

    public List<Transaction> getAllDebitsByUser(User user) {
        List<Invoice> invoices = invoiceService.getAllByUser(user);
        return transactionRepo.findAllByInvoiceRecipientIn(invoices);
    }

    public List<Transaction> getAllCreditsByUser(User user) {
        List<Invoice> invoices = invoiceService.getAllByUser(user);
        return transactionRepo.findAllByInvoiceSenderIn(invoices);
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
