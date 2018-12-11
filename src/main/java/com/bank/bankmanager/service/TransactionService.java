package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.repos.TransactionRepo;
import com.bank.bankmanager.repos.TransactionSearchRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final InvoiceService invoiceService;
    private final TransactionSearchRepo transactionSearchRepo;

    public TransactionService(TransactionRepo transactionRepo, InvoiceService invoiceService, TransactionSearchRepo transactionSearchRepo) {
        this.transactionRepo = transactionRepo;
        this.invoiceService = invoiceService;
        this.transactionSearchRepo = transactionSearchRepo;
    }

    public List<Transaction> getAll() {
        return transactionRepo.findAllByOrderByTstzDesc();
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

    public List<Long> getDistinctInvoiceSenderAll() {
        return transactionRepo.findDistinctInvoiceSenderAll();
    }

    public List<Long> getDistinctInvoiceRecipientAll() {
        return transactionRepo.findDistinctInvoiceRecipientAll();
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

    public List<Transaction> searchTransaction(String text) {
        if ("".equals(text)) {
            return transactionRepo.findAll();
        } else {
            return transactionSearchRepo.searchTransactionByWildcardDate(text);
        }
    }
}
