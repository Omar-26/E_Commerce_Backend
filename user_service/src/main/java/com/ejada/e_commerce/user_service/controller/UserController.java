package com.ejada.e_commerce.user_service.controller;

import com.ejada.e_commerce.common.dto.UserDTO;
import com.ejada.e_commerce.common.exception.UserNotFoundException;
import com.ejada.e_commerce.user_service.model.User;
import com.ejada.e_commerce.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // role : Customer or Seller
//    @GetMapping("user/{id}")
//    public boolean canHaveWallet(@PathVariable Long id) {
//        return userService.canHaveWallet(id);
//    }

    @GetMapping("user/{id}")
    User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    };

    // role : Admin
    @GetMapping("users")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("users/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }
}
