package com.ejada.e_commerce.shop_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;

    @Column(name = "category_id")
    private Long categoryId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantityInStock;

    private Boolean inStock;

    @PrePersist
    @PreUpdate
    private void updateInStock() {
        this.inStock = (this.quantityInStock != null && this.quantityInStock > 0);
    }

    public Product() {
    }

    public Product(Long id, Long sellerId, Long categoryId, String name, String description, BigDecimal price, Integer quantityInStock, Boolean inStock) {
        this.id = id;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.inStock = inStock;
    }
}