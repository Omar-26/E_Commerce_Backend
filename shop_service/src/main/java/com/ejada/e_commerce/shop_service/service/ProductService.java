package com.ejada.e_commerce.shop_service.service;

import com.ejada.e_commerce.common.client.InventoryClient;
import com.ejada.e_commerce.common.dto.InventoryDTO;
import com.ejada.e_commerce.common.exception.ProductNotFoundException;
import com.ejada.e_commerce.shop_service.exception.SellerCannotBeChangedException;
import com.ejada.e_commerce.shop_service.model.Order;
import com.ejada.e_commerce.shop_service.model.OrderItem;
import com.ejada.e_commerce.shop_service.model.Product;
import com.ejada.e_commerce.shop_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;
    private final OrderService orderService;

    public ProductService(ProductRepository productRepository, InventoryClient inventoryClient, OrderService orderService) {
        this.productRepository = productRepository;
        this.inventoryClient = inventoryClient;
        this.orderService = orderService;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));
    }

    public Iterable<Product> getSellerProducts(Long sellerId) {
        return productRepository.findProductsBySellerId(sellerId);
    }

    public Iterable<Product> getCategoryProducts(Long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId);
    }

    public Long createProduct(Product product) {
        Long productId = productRepository.save(product).getId();
        InventoryDTO inventoryDTO = new InventoryDTO(productId, product.getQuantityInStock());
        inventoryClient.addInventory(inventoryDTO);
        return productId;
    }

    public void updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));
        if (!existingProduct.getSellerId().equals(product.getSellerId())) {
            throw new SellerCannotBeChangedException("Seller can't be changed");
        }
        existingProduct.setName(product.getName());
        existingProduct.setCategoryId(product.getCategoryId());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantityInStock(product.getQuantityInStock());
        InventoryDTO inventoryDTO = new InventoryDTO(product.getId(), product.getQuantityInStock());
        inventoryClient.updateInventory(inventoryDTO);
        productRepository.save(existingProduct);
    }

    public Long deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));
        productRepository.delete(product);
        inventoryClient.deleteInventory(productId);
        return productId;
    }

    public Long checkoutProduct(Long customerId, OrderItem orderItem) {
        Order order = new Order();
        order.setCustomerId(customerId);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderedItems(orderItems);

        orderService.placeOrder(order);
        return orderItem.getProductId();
    }
}