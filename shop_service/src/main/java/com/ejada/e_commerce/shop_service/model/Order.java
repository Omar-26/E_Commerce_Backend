package com.ejada.e_commerce.shop_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @ElementCollection
    @CollectionTable(name = "ordered_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderedItems;

    private Boolean isPaid;

    private Boolean isDelivered;

    private Boolean isCancelled;

    private Boolean isReturned;

    private Boolean isShipped;

    private Boolean isPacked;

    private Boolean isProcessed;

    public Order() {
    }

    public Order(Long id, Long customerId, List<OrderItem> orderedItems, Boolean isDelivered, Boolean isPaid, Boolean isCancelled, Boolean isReturned, Boolean isShipped, Boolean isPacked, Boolean isProcessed) {
        this.id = id;
        this.customerId = customerId;
        this.orderedItems = orderedItems;
        this.isDelivered = isDelivered;
        this.isPaid = isPaid;
        this.isCancelled = isCancelled;
        this.isReturned = isReturned;
        this.isShipped = isShipped;
        this.isPacked = isPacked;
        this.isProcessed = isProcessed;
    }
}
