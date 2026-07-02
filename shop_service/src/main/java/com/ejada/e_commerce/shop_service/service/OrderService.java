package com.ejada.e_commerce.shop_service.service;

import com.ejada.e_commerce.shop_service.exception.OrderNotFoundException;
import com.ejada.e_commerce.shop_service.model.Order;
import com.ejada.e_commerce.shop_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Iterable<Order> getCustomerOrders(Long customerId) {
        return orderRepository.findOrdersByCustomerId(customerId);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order doesn't exist"));
    }

    public void placeOrder(Order order) {
        orderRepository.save(order);
    }

    public void cancelOrder(Long orderId) {
        orderRepository.getOrderById(orderId).ifPresentOrElse(orderRepository::delete, () -> {
            throw new OrderNotFoundException("Order doesn't exist");
        });
    }
}
