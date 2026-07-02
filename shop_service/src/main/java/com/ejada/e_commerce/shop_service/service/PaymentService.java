package com.ejada.e_commerce.shop_service.service;

import com.ejada.e_commerce.shop_service.model.Payment;
import com.ejada.e_commerce.shop_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Payment payment) {
        // Integrate with payment gateway API here
        // For example, using Stripe:
        // Stripe.apiKey = "your-stripe-secret-key";
        // Charge charge = Charge.create(params);

        // Simulate payment processing
        payment.setStatus("SUCCESS");
        return paymentRepository.save(payment);
    }
}