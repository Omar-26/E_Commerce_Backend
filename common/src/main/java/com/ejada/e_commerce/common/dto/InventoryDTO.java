package com.ejada.e_commerce.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryDTO {
    private Long productId;
    private Integer quantity;

    public InventoryDTO() {
    }

    public InventoryDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}