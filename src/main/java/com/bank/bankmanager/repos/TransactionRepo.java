package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByOrderByTstzDesc();
    List<Transaction> findAllByInvoiceSenderOrInvoiceRecipient(Invoice invoice, Invoice invoice2);
    List<Transaction> findAllByInvoiceSender(Invoice invoice);
    List<Transaction> findAllByInvoiceRecipient(Invoice invoice);
    List<Transaction> findAllByInvoiceSenderIn(List<Invoice> invoices);
    List<Transaction> findAllByInvoiceRecipientIn(List<Invoice> invoices);

    @Query(value = "SELECT DISTINCT invoice_sender FROM transaction ORDER BY transaction.invoice_sender ASC;", nativeQuery = true)
    List<Long> findDistinctInvoiceSenderAll();
    @Query(value = "SELECT DISTINCT invoice_recipient FROM transaction ORDER BY transaction.invoice_recipient ASC;", nativeQuery = true)
    List<Long> findDistinctInvoiceRecipientAll();
}
