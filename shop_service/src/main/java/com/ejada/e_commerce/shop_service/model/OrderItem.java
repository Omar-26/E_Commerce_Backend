package com.ejada.e_commerce.shop_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class OrderItem {
    private Long productId;
    private Integer quantity;
}
