package com.ejada.e_commerce.wallet_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long walletId;
    private Long userId;
    private BigDecimal amount;
    private String type;
    private String time;

    public Transaction() {
    }

    public Transaction(Long id, Long walletId, Long userId, BigDecimal amount, String type, String time) {
        this.id = id;
        this.walletId = walletId;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.time = time;
    }
}