package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.repos.TransactionRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public List<Long> getDistinctByInvoiceSenderIdByUserOrderByASC(User user) {
        return transactionRepo.findDistinctByInvoiceSenderIdByUserOrderByASC(user);
    }

    public List<Long> getDistinctByInvoiceRecipientIdByUserOrderByASC(User user) {
        return transactionRepo.findDistinctByInvoiceRecipientIdByUserOrderByASC(user);
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

    // TODO: сделать поиск на Lucene
    public List<Transaction> searchTransaction(User searchUser, Invoice invoiceSender, Invoice invoiceRecipient, String textDateOn, String textDateOff) {
        // check
        boolean isNotNullSearchUser       = searchUser != null;
        boolean isNotNullDateOn           = !"".equals(textDateOn);
        boolean isNotNullDateOff          = !"".equals(textDateOff);
        boolean isNotNullInvoiceSender    = invoiceSender != null;
        boolean isNotNullInvoiceRecipient = invoiceRecipient != null;

        // Parse String to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateOn = null;
        LocalDateTime dateOff = null;
        if (!"".equals(textDateOn) && !"".equals(textDateOff)) {
            textDateOn += " 00:00:00.000";
            textDateOff += " 00:00:00.000";
            dateOn = LocalDateTime.parse(textDateOn, formatter);
            dateOff = LocalDateTime.parse(textDateOff, formatter);
        } else if (!"".equals(textDateOn)) {
            textDateOff = textDateOn;
            textDateOn += " 00:00:00.000";
            textDateOff += " 23:59:59.999";
            dateOn = LocalDateTime.parse(textDateOn, formatter);
            dateOff = LocalDateTime.parse(textDateOff, formatter);
        }

        if (isNotNullSearchUser && !isNotNullInvoiceSender && !isNotNullInvoiceRecipient) { // with User

            if (!isNotNullDateOn && !isNotNullDateOff)
                return transactionRepo.findAllByUserOrderByTstzDesc(searchUser);

            if (isNotNullDateOn && !isNotNullDateOff)
                return transactionRepo.findAllByUserAndTstzBetweenOrderByTstzDesc(searchUser, dateOn, dateOff);

            if (isNotNullDateOn && isNotNullDateOff)
                return transactionRepo.findAllByUserAndTstzBetweenOrderByTstzDesc(searchUser, dateOn, dateOff);

        } else { // Without User
            // all
            if (isNotNullDateOn && isNotNullDateOff && isNotNullInvoiceSender && isNotNullInvoiceRecipient)
                return transactionRepo
                        .findTransactionByInvoiceSenderAndInvoiceRecipientByTstzBetweenOrderByTstzDesc(
                                invoiceSender, invoiceRecipient, dateOn, dateOff
                        );

            // date & invoice sender
            if (isNotNullDateOn && isNotNullDateOff && isNotNullInvoiceSender && !isNotNullInvoiceRecipient)
                return transactionRepo.findTransactionByInvoiceSenderAndTstzOrderByTstzDesc(
                        invoiceSender, dateOn,dateOff
                );

            // date & invoice recipient
            if (isNotNullDateOn && isNotNullDateOff && !isNotNullInvoiceSender && isNotNullInvoiceRecipient)
                return transactionRepo.findTransactionByInvoiceRecipientAndTstzOrderByTstzDesc(
                        invoiceRecipient, dateOn,dateOff
                );

            // only date
            if (isNotNullDateOn && isNotNullDateOff && !isNotNullInvoiceSender && !isNotNullInvoiceRecipient)
                return transactionRepo.findTransactionByTstzBetweenOrderByTstzDesc(
                        dateOn, dateOff
                );

            // only invoices
            if (!isNotNullDateOn && !isNotNullDateOff && isNotNullInvoiceSender && isNotNullInvoiceRecipient)
                return transactionRepo.findTransactionsByInvoiceSenderAndInvoiceRecipientOrderByTstzDesc(
                        invoiceSender, invoiceRecipient
                );

            // invoice sender
            if (!isNotNullDateOn && !isNotNullDateOff && isNotNullInvoiceSender && !isNotNullInvoiceRecipient)
                return transactionRepo.findTransactionsByInvoiceSenderOrderByTstzDesc(
                        invoiceSender
                );

            // invoice recipient
            if (!isNotNullDateOn && !isNotNullDateOff && !isNotNullInvoiceSender && isNotNullInvoiceRecipient)
                return transactionRepo.findTransactionsByInvoiceRecipientOrderByTstzDesc(
                        invoiceRecipient
                );
        }

        return getAll();
    }

}
