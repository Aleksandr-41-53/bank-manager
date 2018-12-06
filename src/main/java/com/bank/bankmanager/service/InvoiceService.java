package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.repos.InvoiceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepo invoiceRepo;

    public InvoiceService(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    public List<Invoice> getInvoicesByUser(User user) {
        return invoiceRepo.findAllByClient(user);
    }

    public boolean addInvoice(Invoice invoice, String number, User client) {
        Invoice invoiceFromDb = invoiceRepo.findByClient(client);
        if (invoiceFromDb != null) return false;

        invoice.setNumber(number);
        invoice.setClient(client);
        invoiceRepo.save(invoice);
        return true;
    }
}
