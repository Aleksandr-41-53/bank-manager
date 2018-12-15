package com.bank.bankmanager.domain.dto;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;

import java.math.BigDecimal;
import java.util.Set;

public class InvoiceDto {
    private Long id;
    private User client;
    private String number;
    private BigDecimal cash;
    private Set<Transaction> sender;
    private Set<Transaction> recipient;

    public InvoiceDto(Invoice invoice) {
        this.id = invoice.getId();
        this.client = invoice.getClient();
        this.number = invoice.getNumber();
        this.cash = invoice.getCash();
        this.sender = invoice.getSender();
        this.recipient = invoice.getRecipient();
    }

    public InvoiceDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Set<Transaction> getSender() {
        return sender;
    }

    public void setSender(Set<Transaction> sender) {
        this.sender = sender;
    }

    public Set<Transaction> getRecipient() {
        return recipient;
    }

    public void setRecipient(Set<Transaction> recipient) {
        this.recipient = recipient;
    }
}
