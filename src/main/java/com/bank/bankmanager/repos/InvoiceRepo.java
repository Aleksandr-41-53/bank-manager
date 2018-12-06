package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByClient(User user);
    Invoice findByClient(User client);
}
