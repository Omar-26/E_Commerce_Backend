package com.ejada.e_commerce.wallet_service.repository;

import com.ejada.e_commerce.wallet_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Iterable<Transaction> findTransactionsByUserId(Long userId);
    Iterable<Transaction> findTransactionsByWalletId(Long walletId);
}
