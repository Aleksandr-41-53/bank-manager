package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByInvoiceSenderOrInvoiceRecipient(Invoice invoice, Invoice invoice2);
    List<Transaction> findAllByInvoiceSender(Invoice invoice);
    List<Transaction> findAllByInvoiceRecipient(Invoice invoice);
    List<Transaction> findAllByInvoiceSenderIn(List<Invoice> invoices);
    List<Transaction> findAllByInvoiceRecipientIn(List<Invoice> invoices);
}
