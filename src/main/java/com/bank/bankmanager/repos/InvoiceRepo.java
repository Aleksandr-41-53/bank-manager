package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
}
