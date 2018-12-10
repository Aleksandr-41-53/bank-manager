package com.bank.bankmanager.domain.dto;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {
    private Long id;
    private BigDecimal cash;
    private BigDecimal senderCash;
    private BigDecimal recipientCash;
    private Invoice invoiceSender;
    private Invoice invoiceRecipient;
    private Date tstz;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.cash = transaction.getCash();
        this.senderCash = transaction.getSenderCash();
        this.recipientCash = transaction.getRecipientCash();
        this.invoiceSender = transaction.getInvoiceSender();
        this.invoiceRecipient = transaction.getInvoiceRecipient();
        this.tstz = transaction.getTstz();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getSenderCash() {
        return senderCash;
    }

    public void setSenderCash(BigDecimal senderCash) {
        this.senderCash = senderCash;
    }

    public BigDecimal getRecipientCash() {
        return recipientCash;
    }

    public void setRecipientCash(BigDecimal recipientCash) {
        this.recipientCash = recipientCash;
    }

    public Invoice getInvoiceSender() {
        return invoiceSender;
    }

    public void setInvoiceSender(Invoice invoiceSender) {
        this.invoiceSender = invoiceSender;
    }

    public Invoice getInvoiceRecipient() {
        return invoiceRecipient;
    }

    public void setInvoiceRecipient(Invoice invoiceRecipient) {
        this.invoiceRecipient = invoiceRecipient;
    }

    public Date getTstz() {
        return tstz;
    }

    public void setTstz(Date tstz) {
        this.tstz = tstz;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                    "id=" + id +
                    ", cash=" + cash +
                    ", senderCash=" + senderCash +
                    ", recipientCash=" + recipientCash +
                    ", invoiceSender=" + invoiceSender +
                    ", invoiceRecipient=" + invoiceRecipient +
                    ", tstz=" + tstz +
                "}";
    }
}
