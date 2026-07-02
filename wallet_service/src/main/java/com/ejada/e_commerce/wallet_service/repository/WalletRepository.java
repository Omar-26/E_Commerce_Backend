package com.ejada.e_commerce.wallet_service.repository;

import com.ejada.e_commerce.wallet_service.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findWalletByIdAndUserId(Long userId, Long walletId);

    Iterable<Wallet> findWalletsByUserId(Long userId);

    Optional<Wallet> findWalletById(Long walletId);
}
