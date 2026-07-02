package com.e_commerce.inventory_service.service;

import com.e_commerce.inventory_service.model.Inventory;
import com.e_commerce.inventory_service.repository.InventoryRepository;
import com.ejada.e_commerce.common.dto.InventoryDTO;
import com.ejada.e_commerce.common.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory getInventory(Long productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));
    }

    public void addInventory(InventoryDTO inventoryDTO) {
        Inventory newInventory = new Inventory(inventoryDTO.getProductId(), inventoryDTO.getQuantity());
        inventoryRepository.save(newInventory);
    }

    public void updateInventory(InventoryDTO inventoryDTO) {
        inventoryRepository.findById(inventoryDTO.getProductId()).ifPresent(
                existingInventory -> {
                    existingInventory.setQuantity(inventoryDTO.getQuantity());
                    inventoryRepository.save(existingInventory);
                });
    }

    public void deleteInventory(Long productId) {
        inventoryRepository.findById(productId).ifPresent(inventoryRepository::delete);
    }
}