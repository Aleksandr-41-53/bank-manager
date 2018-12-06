package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.repos.InvoiceRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class InvoiceService {

    private final InvoiceRepo invoiceRepo;

    public InvoiceService(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    public List<Invoice> getInvoicesByUser(User user) {
        return invoiceRepo.findAllByClient(user);
    }

    // TODO: Заглушка, возврашает рандом
    public String getGenerateNumberFor3Order() {
        Random random = new Random();
        return String.valueOf(random.nextInt(10));
    }

    public String getGenerateNumberFor5Order() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9000000) + 1000000);
    }

    public boolean addInvoice(User user, Map<String, String> form) {
        String number = "";
        for (String key : form.keySet()) {
            if ("order".equals(String.format("%.5s", key))) {
                number += form.get(key);
            }
        }

        Invoice invoiceFromDb = invoiceRepo.findByNumber(number);
        if (invoiceFromDb != null) return false;

        Invoice invoice = new Invoice();
        invoice.setCash(BigDecimal.valueOf(0));
        invoice.setNumber(number);
        invoice.setClient(user);
        invoiceRepo.save(invoice);
        return true;
    }
}
