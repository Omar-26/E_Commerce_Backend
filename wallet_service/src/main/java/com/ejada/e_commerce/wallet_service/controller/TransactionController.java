package com.ejada.e_commerce.wallet_service.controller;

import com.ejada.e_commerce.wallet_service.model.Transaction;
import com.ejada.e_commerce.wallet_service.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTransactionsByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionsByUserId(userId));
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<?> getTransactionsByWalletId(@PathVariable Long walletId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionsByWalletId(walletId));
    }

    @PostMapping("/do-transaction")
    public ResponseEntity<?> doTransaction(@RequestBody Transaction transaction) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("wallet id", transaction.getWalletId(),
                        "new balance: ", transactionService.doTransaction(transaction)));
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<?> handleParseException(ParseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }
}