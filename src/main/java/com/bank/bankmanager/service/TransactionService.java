package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.repos.TransactionRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return transactionRepo.findAllByOrderByTstzDesc();
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

        BigDecimal tmp = sender.getCash().subtract(cash);
        if (tmp.compareTo(BigDecimal.ZERO) < 0) return false;

        transaction.setCash(cash);
        transaction.setSenderCash(sender.getCash());
        transaction.setRecipientCash(recipient.getCash());
        transaction.setInvoiceSender(sender);
        transaction.setInvoiceRecipient(recipient);
        transaction.setTstz(LocalDateTime.now());

        sender.setCash(tmp);
        recipient.setCash(recipient.getCash().add(cash));

        transactionRepo.save(transaction);
        invoiceService.save(sender);
        invoiceService.save(recipient);

        return true;
    }

    // TODO: сделать поиск
    public List<Transaction> searchTransaction(Long senderId, Long recipientId, String textDateOn, String textDateOff) {
        if ("".equals(textDateOn) && "".equals(textDateOff)) {
            return transactionRepo.findAll();
        } else {
            textDateOn += " 00:00:00.000";
            textDateOff += " 00:00:00.000";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime dateOn = LocalDateTime.parse(textDateOn, formatter);
            LocalDateTime dateOff = LocalDateTime.parse(textDateOff, formatter);

            return transactionRepo.findTransactionByTstzEquals(dateOn, dateOff);
        }
    }
}
