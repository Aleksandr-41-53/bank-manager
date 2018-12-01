package com.bank.bankmanager.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User usr;

    @NotBlank(message = "Invoice number cannot be empty!")
    @Length(max = 30, message = "Invoice number to long (more than 30)")
    private String number;

    @DecimalMin(value = "0", inclusive = false)
    @Digits(integer = 1000000000, fraction = 2)
    private BigDecimal cash;



}
