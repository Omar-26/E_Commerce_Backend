package com.ejada.e_commerce.shop_service.repository;

import com.ejada.e_commerce.shop_service.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}