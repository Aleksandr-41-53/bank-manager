package com.bank.bankmanager.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cash", columnDefinition = "DECIMAL(14,2)")
    private BigDecimal cash;

    @Column(name = "senderCash", columnDefinition = "DECIMAL(14,2)")
    private BigDecimal senderCash;

    @Column(name = "recipientCash", columnDefinition = "DECIMAL(14,2)")
    private BigDecimal recipientCash;

    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "tstz", columnDefinition= "TIMESTAMP WITH TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC')")
    private Date tstz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_sender")
    private Invoice invoiceSender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_recipient")
    private Invoice invoiceRecipient;

    public Transaction() {
    }

    public Transaction(BigDecimal cash, BigDecimal senderCash, BigDecimal recipientCash) {
        this.cash = cash;
        this.senderCash = senderCash;
        this.recipientCash = recipientCash;
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

    public Date getTstz() {
        return tstz;
    }

    public void setTstz(Date tstz) {
        this.tstz = tstz;
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
}
