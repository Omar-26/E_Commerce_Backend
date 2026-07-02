package com.ejada.e_commerce.shop_service.repository;

import com.ejada.e_commerce.shop_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    Iterable<Product> findProductsBySellerId(Long sellerId);
    Iterable<Product> findProductsByCategoryId(Long categoryId);
}
