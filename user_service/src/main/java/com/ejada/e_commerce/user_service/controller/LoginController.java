package com.ejada.e_commerce.user_service.controller;

import com.ejada.e_commerce.common.exception.UserNotFoundException;
import com.ejada.e_commerce.user_service.exception.InvalidPasswordException;
import com.ejada.e_commerce.user_service.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        String jwt = loginService.login(email, password);
        return (jwt == null) ?
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                : ResponseEntity.status(HttpStatus.OK).body(Map.of("jwt", jwt));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}