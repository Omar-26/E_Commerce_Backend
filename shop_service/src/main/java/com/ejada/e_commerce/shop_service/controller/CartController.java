package com.ejada.e_commerce.shop_service.controller;

import com.ejada.e_commerce.shop_service.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}/products")
    public ResponseEntity<?> getCartProducts(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "Total Price:", cartService.getCartTotal(customerId),
                "Products:", cartService.getCartProducts(customerId)));
    }

    @PostMapping("/{customerId}/add-to-cart/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long customerId, @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Added Product to Cart with Id: %d", cartService.addToCart(customerId, productId)));
    }

    @DeleteMapping("/{customerId}/remove-from-cart/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Removed Product from Cart with Id: %d", cartService.removeFromCart(customerId, productId)));
    }

    @DeleteMapping("/{customerId}/checkout")
    public ResponseEntity<String> checkoutCart(@PathVariable Long customerId) {
        cartService.checkoutCart(customerId);
        return ResponseEntity.status(HttpStatus.OK).body("Cart Checked Out");
    }
}