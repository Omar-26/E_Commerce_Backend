package com.e_commerce.inventory_service.controller;

import com.e_commerce.inventory_service.model.Inventory;
import com.e_commerce.inventory_service.service.InventoryService;
import com.ejada.e_commerce.common.dto.InventoryDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public Inventory getInventory(@PathVariable Long productId) {
        return inventoryService.getInventory(productId);
    }

    @PostMapping("add-inventory")
    public void addInventory(@RequestBody InventoryDTO inventoryDTO) {
        inventoryService.addInventory(inventoryDTO);
    }

    @PutMapping("/update-inventory")
    public void updateInventory(@RequestBody InventoryDTO inventoryDTO) {
        inventoryService.updateInventory(inventoryDTO);
    }

    @DeleteMapping("/{productId}/delete-inventory")
    public void deleteInventory(@PathVariable Long productId) {
        inventoryService.deleteInventory(productId);
    }
}