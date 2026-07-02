package com.ejada.e_commerce.shop_service.controller;

import com.ejada.e_commerce.common.exception.ProductNotFoundException;
import com.ejada.e_commerce.shop_service.model.OrderItem;
import com.ejada.e_commerce.shop_service.model.Product;
import com.ejada.e_commerce.shop_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Iterable<Product>> getSellerProducts(@PathVariable Long sellerId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getSellerProducts(sellerId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Iterable<Product>> getCategoryProducts(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getCategoryProducts(categoryId));
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Added Product with Id: %d", productService.createProduct(product)));
    }

    @PostMapping("{customerId}/checkout")
    public ResponseEntity<String> buyProduct(@PathVariable Long customerId , @RequestBody OrderItem orderItem) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Placed Order fot the Product with Id: %d", productService.checkoutProduct(customerId,orderItem)));
    }

    @PutMapping("/update-product")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Updated Product with Id: %d", product.getId()));
    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Deleted Product with Id: %d", productService.deleteProduct(productId)));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}