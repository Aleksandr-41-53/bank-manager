package com.bank.bankmanager.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User client;

    @NotBlank(message = "Invoice number cannot be empty!")
    private String number;

    @Column(name = "cash", columnDefinition = "DECIMAL(14,2) DEFAULT '0.00'")
    private BigDecimal cash;

    @OneToMany(mappedBy = "invoiceSender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> sender;

    @OneToMany(mappedBy = "invoiceRecipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> recipient;

    public Invoice() {
    }

    public Invoice(User user, String number, BigDecimal cash) {
        this.client = user;
        this.number = number;
        this.cash = cash;
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
