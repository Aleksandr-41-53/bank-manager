package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
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

    @Query(value = "SELECT DISTINCT invoice_sender FROM transaction ORDER BY transaction.invoice_sender ASC;", nativeQuery = true)
    List<Long> findDistinctInvoiceSenderAll();
    @Query(value = "SELECT DISTINCT invoice_recipient FROM transaction ORDER BY transaction.invoice_recipient ASC;", nativeQuery = true)
    List<Long> findDistinctInvoiceRecipientAll();


    // TODO: переделать на Lucene
    // search all
    @Query("SELECT t FROM Transaction t WHERE t.invoiceSender = :sender AND t.invoiceRecipient = :recipient AND t.tstz BETWEEN :dateOn AND :dateOff ORDER BY tstz DESC")
    List<Transaction> findTransactionByInvoiceSenderAndInvoiceRecipientByTstzBetweenOrderByTstzDesc(
            @Param("sender") Invoice sender,
            @Param("recipient") Invoice recipient,
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search date & invoice sender
    @Query("SELECT t FROM Transaction t WHERE t.invoiceSender = :sender AND t.tstz BETWEEN :dateOn AND :dateOff ORDER BY tstz DESC")
    List<Transaction> findTransactionByInvoiceSenderAndTstzOrderByTstzDesc(
            @Param("sender") Invoice sender,
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search date & invoice recipient
    @Query("SELECT t FROM Transaction t WHERE t.invoiceRecipient = :recipient AND t.tstz BETWEEN :dateOn AND :dateOff ORDER BY tstz DESC")
    List<Transaction> findTransactionByInvoiceRecipientAndTstzOrderByTstzDesc(
            @Param("recipient") Invoice recipient,
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search date
    @Query("SELECT t FROM Transaction t WHERE t.tstz BETWEEN :dateOn AND :dateOff ORDER BY tstz DESC")
    List<Transaction> findTransactionByTstzBetweenOrderByTstzDesc(
            @Param("dateOn") LocalDateTime dateOn,
            @Param("dateOff") LocalDateTime dateOff
    );

    // search invoices
    @Query("SELECT t FROM Transaction t WHERE t.invoiceSender = :sender AND t.invoiceRecipient = :recipient ORDER BY tstz DESC")
    List<Transaction> findTransactionsByInvoiceSenderAndInvoiceRecipientOrderByTstzDesc(
            @Param("sender") Invoice sender,
            @Param("recipient") Invoice recipient
    );

    // search invoice sender
    @Query("SELECT t FROM Transaction t WHERE t.invoiceSender = :sender ORDER BY tstz DESC")
    List<Transaction> findTransactionsByInvoiceSenderOrderByTstzDesc(
            @Param("sender") Invoice sender
    );

    // search invoice recipient
    @Query("SELECT t FROM Transaction t WHERE t.invoiceRecipient = :recipient ORDER BY tstz DESC")
    List<Transaction> findTransactionsByInvoiceRecipientOrderByTstzDesc(
            @Param("recipient") Invoice recipient
    );
}
