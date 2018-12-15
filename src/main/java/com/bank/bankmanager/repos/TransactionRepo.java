package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByOrderByTstzDesc();
    List<Transaction> findAllByInvoiceSender(Invoice invoice);
    List<Transaction> findAllByInvoiceRecipient(Invoice invoice);
    List<Transaction> findAllByInvoiceSenderIn(List<Invoice> invoices);
    List<Transaction> findAllByInvoiceRecipientIn(List<Invoice> invoices);

    // Create search form
    @Query(value = "SELECT DISTINCT invoice_sender FROM transaction ORDER BY transaction.invoice_sender ASC;", nativeQuery = true)
    List<Long> findDistinctInvoiceSenderAll();

    @Query(value = "SELECT DISTINCT invoice_recipient FROM transaction ORDER BY transaction.invoice_recipient ASC;", nativeQuery = true)
    List<Long> findDistinctInvoiceRecipientAll();

    @Query(value = "SELECT DISTINCT t.invoiceSender.id FROM Invoice i, Transaction t WHERE i.client = :user AND t.invoiceSender.id = i.id ORDER BY t.invoiceSender ASC")
    List<Long> findDistinctByInvoiceSenderIdByUserOrderByASC(@Param("user") User user);

    @Query(value = "SELECT DISTINCT t.invoiceRecipient.id FROM Invoice i, Transaction t WHERE i.client = :user AND t.invoiceRecipient.id = i.id ORDER BY t.invoiceRecipient ASC")
    List<Long> findDistinctByInvoiceRecipientIdByUserOrderByASC(@Param("user") User user);

    // TODO: переделать на Lucene
    // search User
    @Query(value = "SELECT DISTINCT transaction.* " +
            "FROM invoice, transaction " +
            "WHERE invoice.user_id = :user " +
            "AND (invoice_sender = invoice.id) <> (invoice_recipient = invoice.id) " +
            "ORDER BY tstz DESC", nativeQuery = true)
    List<Transaction> findAllByUserOrderByTstzDesc(@Param("user") User user);

    // User & date
    @Query(value = "SELECT DISTINCT transaction.* " +
            "FROM invoice, transaction " +
            "WHERE invoice.user_id = :user " +
            "AND (invoice_sender = invoice.id) <> (invoice_recipient = invoice.id) " +
            "AND tstz BETWEEN :dateOn AND :dateOff " +
            "ORDER BY tstz DESC", nativeQuery = true)
    List<Transaction> findAllByUserAndTstzBetweenOrderByTstzDesc(
            @Param("user") User user,
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search all
    @Query(value = "SELECT t FROM Transaction t WHERE t.invoiceSender = :sender AND t.invoiceRecipient = :recipient AND t.tstz BETWEEN :dateOn AND :dateOff ORDER BY tstz DESC")
    List<Transaction> findTransactionByInvoiceSenderAndInvoiceRecipientByTstzBetweenOrderByTstzDesc(
            @Param("sender") Invoice sender,
            @Param("recipient") Invoice recipient,
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search date & invoice sender
    @Query(value = "SELECT t FROM Transaction t WHERE t.invoiceSender = :sender AND t.tstz BETWEEN :dateOn AND :dateOff ORDER BY tstz DESC")
    List<Transaction> findTransactionByInvoiceSenderAndTstzOrderByTstzDesc(
            @Param("sender") Invoice sender,
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search date & invoice recipient
    @Query(value = "SELECT t FROM Transaction t WHERE t.invoiceRecipient = :recipient AND t.tstz BETWEEN :dateOn AND :dateOff ORDER BY tstz DESC")
    List<Transaction> findTransactionByInvoiceRecipientAndTstzOrderByTstzDesc(
            @Param("recipient") Invoice recipient,
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search date
    @Query(value = "SELECT t FROM Transaction t WHERE t.tstz BETWEEN :dateOn AND :dateOff ORDER BY tstz DESC")
    List<Transaction> findTransactionByTstzBetweenOrderByTstzDesc(
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search invoices
    @Query(value = "SELECT t FROM Transaction t WHERE t.invoiceSender = :sender AND t.invoiceRecipient = :recipient ORDER BY tstz DESC")
    List<Transaction> findTransactionsByInvoiceSenderAndInvoiceRecipientOrderByTstzDesc(
            @Param("sender") Invoice sender,
            @Param("recipient") Invoice recipient
    );

    // search invoice sender
    @Query(value = "SELECT t FROM Transaction t WHERE t.invoiceSender = :sender ORDER BY tstz DESC")
    List<Transaction> findTransactionsByInvoiceSenderOrderByTstzDesc(
            @Param("sender") Invoice sender
    );

    // search invoice recipient
    @Query(value = "SELECT t FROM Transaction t WHERE t.invoiceRecipient = :recipient ORDER BY tstz DESC")
    List<Transaction> findTransactionsByInvoiceRecipientOrderByTstzDesc(
            @Param("recipient") Invoice recipient
    );
}
