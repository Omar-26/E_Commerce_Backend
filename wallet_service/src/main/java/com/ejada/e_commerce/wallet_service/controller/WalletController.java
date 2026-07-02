package com.ejada.e_commerce.wallet_service.controller;


import com.ejada.e_commerce.common.exception.UserNotFoundException;
import com.ejada.e_commerce.wallet_service.exception.WalletNotFoundException;
import com.ejada.e_commerce.wallet_service.model.Wallet;
import com.ejada.e_commerce.wallet_service.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}/wallets")
    public ResponseEntity<Iterable<Wallet>> getUserWallets(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.getWalletsByUserId(userId));
    }

    @PostMapping("/{userId}/create-wallet")
    public ResponseEntity<Wallet> createWallet(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.createWallet(userId));
    }

    @DeleteMapping("/delete-wallet/{walletId}")
    public ResponseEntity<String> deleteWallet(@PathVariable Long walletId) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Deleted Wallet with Id: %d", walletService.deleteWallet(walletId)));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<String> handleWalletNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
