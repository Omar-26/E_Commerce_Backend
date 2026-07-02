package com.ejada.e_commerce.wallet_service.service;

import com.ejada.e_commerce.wallet_service.model.Transaction;
import com.ejada.e_commerce.wallet_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    public TransactionService(TransactionRepository transactionRepository, WalletService walletService) {
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
    }

    public BigDecimal doTransaction(Transaction transaction) {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a - dd-MMM-yyyy");
        String formattedDate = formatter.format(new Date());
        transaction.setTime(formattedDate);
        transactionRepository.save(transaction);
        return walletService.doTransaction(transaction);
    }

    public Iterable<Transaction> getTransactionsByWalletId(Long walletId) {
        walletService.getWalletById(walletId);
        return transactionRepository.findTransactionsByWalletId(walletId);
    }

    public Iterable<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findTransactionsByUserId(userId);
    }
}
