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

    @ManyToMany
    @JoinTable(
            name = "motion",
            joinColumns = { @JoinColumn(name = "sender_invoice_id") },
            inverseJoinColumns = { @JoinColumn(name = "recipient_invoice_id") }
    )
    private Set<Invoice> transactMotion = new HashSet<>();

    @Column(name = "cash", columnDefinition = "MONEY")
    private BigDecimal cash;

    @Temporal(TemporalType.TIME)
    private Date time;

    @Temporal(TemporalType.DATE)
    private Date date;


    public Transaction() {
    }

    public Transaction(BigDecimal cash, Date time, Date date) {
        this.cash = cash;
        this.time = time;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Invoice> getTransactMotion() {
        return transactMotion;
    }

    public void setTransactMotion(Set<Invoice> transactMotion) {
        this.transactMotion = transactMotion;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
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
}
