package com.ejada.e_commerce.common.client;

import com.ejada.e_commerce.common.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory", url = "http://localhost:8300/inventory")
public interface InventoryClient {

    @GetMapping("/{productId}")
    InventoryDTO getInventory(@PathVariable Long productId);

    @PostMapping("/add-inventory")
    void addInventory(@RequestBody InventoryDTO inventoryDTO);

    @PutMapping("/update-inventory")
    void updateInventory(@RequestBody InventoryDTO inventory);

    @DeleteMapping("/{productId}/delete-inventory")
    void deleteInventory(@PathVariable Long productId);
}