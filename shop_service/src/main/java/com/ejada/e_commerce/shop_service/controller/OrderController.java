package com.ejada.e_commerce.shop_service.controller;

import com.ejada.e_commerce.shop_service.exception.OrderNotFoundException;
import com.ejada.e_commerce.shop_service.model.Order;
import com.ejada.e_commerce.shop_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{customerId}/all-orders")
    public ResponseEntity<Iterable<Order>> getCustomerOrders(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getCustomerOrders(customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(String.format("Cancelled Order with Id: %d", orderId));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}