package com.bank.bankmanager.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cash", columnDefinition = "DECIMAL(14,2)")
    private BigDecimal cash;

    @Column(name = "invoiceCash", columnDefinition = "DECIMAL(14,2)")
    private BigDecimal invoiceCash;

    @Temporal(TemporalType.TIME)
    private Date time;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_sender")
    private Invoice invoiceSender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_recipient")
    private Invoice invoiceRecipient;

    public Transaction() {
    }

    public Transaction(BigDecimal cash, BigDecimal invoiceCash, TransactionType type) {
        this.cash = cash;
        this.invoiceCash = invoiceCash;
        this.type = type;
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

    public BigDecimal getInvoiceCash() {
        return invoiceCash;
    }

    public void setInvoiceCash(BigDecimal invoiceCash) {
        this.invoiceCash = invoiceCash;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
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
