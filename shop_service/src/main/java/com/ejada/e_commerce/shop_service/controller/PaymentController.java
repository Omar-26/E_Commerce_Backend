package com.ejada.e_commerce.shop_service.controller;

import com.ejada.e_commerce.shop_service.model.Payment;
import com.ejada.e_commerce.shop_service.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.processPayment(payment));
    }
}