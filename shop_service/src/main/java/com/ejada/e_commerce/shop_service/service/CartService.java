package com.ejada.e_commerce.shop_service.service;

import com.ejada.e_commerce.shop_service.exception.CartIsEmptyException;
import com.ejada.e_commerce.shop_service.model.Cart;
import com.ejada.e_commerce.shop_service.model.Order;
import com.ejada.e_commerce.shop_service.model.OrderItem;
import com.ejada.e_commerce.shop_service.model.Product;
import com.ejada.e_commerce.shop_service.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final OrderService orderService;

    public CartService(CartRepository cartRepository, ProductService productService, OrderService orderService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.orderService = orderService;
    }

    public Cart getCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCustomerId(customerId);
            newCart.setProducts(new HashMap<>());
            return cartRepository.save(newCart);
        });
    }

    public List<OrderItem> getCartProducts(Long customerId) {
        Cart cart = this.getCart(customerId);
        Map<Product, Integer> products = cart.getProducts();

        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(entry.getKey().getId());
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);
        }
        return orderItems;
    }
    public Long addToCart(Long customerId, Long productId) {
        Product product = productService.getProductById(productId);
        Cart cart = this.getCart(customerId);

        Map<Product, Integer> products = cart.getProducts();
        // Add the specified product to the cart, incrementing its quantity by 1 if it already exists
        products.put(product, products.getOrDefault(product, 0) + 1);
        cart.setProducts(products);
        cartRepository.save(cart);
        return productId;
    }

    public Long removeFromCart(Long customerId, Long productId) {
        Product product = productService.getProductById(productId);
        Cart cart = getCart(customerId);

        Map<Product, Integer> products = cart.getProducts();
        if (products.isEmpty()) {
            throw new CartIsEmptyException("Cart is empty.");
        }
        if (products.containsKey(product)) {
            int quantity = products.get(product);
            if (quantity > 1) {
                products.put(product, quantity - 1);
            } else if (quantity == 0) {
                throw new IllegalArgumentException("Product not found in cart.");
            } else {
                products.remove(product);
            }
        }
        cart.setProducts(products);
        cartRepository.save(cart);
        return productId;
    }

    public void checkoutCart(Long customerId) {
        Cart cart = this.getCart(customerId);
        Order order = new Order();
        order.setCustomerId(customerId);
        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(entry.getKey().getId());
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);
        }
        order.setOrderedItems(orderItems);
        orderService.placeOrder(order);
        this.clearCart(customerId);
    }

    public void clearCart(Long customerId) {
        Cart cart = this.getCart(customerId);
        cart.setProducts(new HashMap<>());
        cartRepository.save(cart);
    }

    public BigDecimal getCartTotal(Long customerId) {
        Cart cart = this.getCart(customerId);
        return cart.getProducts().entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}