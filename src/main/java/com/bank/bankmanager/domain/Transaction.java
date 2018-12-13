package com.bank.bankmanager.domain;

import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Indexed
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cash", columnDefinition = "DECIMAL(14,2)")
    private BigDecimal cash;

    @Column(name = "sender_cash", columnDefinition = "DECIMAL(14,2)")
    private BigDecimal senderCash;

    @Column(name = "recipient_cash", columnDefinition = "DECIMAL(14,2)")
    private BigDecimal recipientCash;

    @Column(name = "tstz", columnDefinition = "timestamp without time zone")
    private LocalDateTime tstz;

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

    public LocalDateTime getTstz() {
        return tstz;
    }

    public void setTstz(LocalDateTime tstz) {
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
