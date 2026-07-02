package com.ejada.e_commerce.shop_service.repository;

import com.ejada.e_commerce.shop_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Iterable<Order> findOrdersByCustomerId(Long customerId);

    Optional<Order> getOrderById(Long orderId);
}
