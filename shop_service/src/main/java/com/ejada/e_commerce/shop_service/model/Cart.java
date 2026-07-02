package com.ejada.e_commerce.shop_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @ElementCollection
    @CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "orderedQuantity")
    private Map<Product, Integer> products = new HashMap<>();

    private BigDecimal totalPrice;

    public Cart() {
    }

    public Cart(Long id, Long customerId, Map<Product, Integer> products, BigDecimal totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
