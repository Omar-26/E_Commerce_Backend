package com.ejada.e_commerce.wallet_service.service;

import com.ejada.e_commerce.common.client.UserClient;
import com.ejada.e_commerce.common.dto.UserDTO;
import com.ejada.e_commerce.common.exception.UserNotFoundException;
import com.ejada.e_commerce.wallet_service.exception.WalletNotFoundException;
import com.ejada.e_commerce.wallet_service.model.Transaction;
import com.ejada.e_commerce.wallet_service.model.Wallet;
import com.ejada.e_commerce.wallet_service.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserClient userClient;

    public WalletService(WalletRepository walletRepository, UserClient userClient) {
        this.walletRepository = walletRepository;
        this.userClient = userClient;
    }

    public Wallet createWallet(Long userId) {
        UserDTO user = userClient.getUserById(userId);
        if (user != null) {
            Wallet wallet = new Wallet();
            wallet.setUserId(userId);
            return walletRepository.save(wallet);
        }
        throw new UserNotFoundException("User doesn't exist");
    }

    public Long deleteWallet(Long walletId) {
        Wallet wallet = this.getWalletById(walletId);
        walletRepository.delete(wallet);
        return wallet.getId();
    }

    public Iterable<Wallet> getWalletsByUserId(Long userId) {
        return walletRepository.findWalletsByUserId(userId);
    }

    public Wallet getWalletById(Long walletId) {
        return walletRepository.findWalletById(walletId).orElseThrow(() -> new WalletNotFoundException("Wallet doesn't exist"));
    }

    public BigDecimal doTransaction(Transaction transaction) {
        Wallet wallet = this.getWalletById(transaction.getWalletId());
        if (transaction.getType().equalsIgnoreCase("DEPOSIT")) {
            BigDecimal newBalance = wallet.getBalance().add(transaction.getAmount());
            wallet.setBalance(newBalance);
        } else if (transaction.getType().equalsIgnoreCase("WITHDRAW")) {
            BigDecimal newBalance = wallet.getBalance().subtract(transaction.getAmount());
            wallet.setBalance(newBalance);
        }
        walletRepository.save(wallet);
        return wallet.getBalance();
    }
}