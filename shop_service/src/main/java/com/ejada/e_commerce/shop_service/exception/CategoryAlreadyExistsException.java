package com.ejada.e_commerce.shop_service.exception;

public class CategoryAlreadyExistsException extends IllegalArgumentException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
