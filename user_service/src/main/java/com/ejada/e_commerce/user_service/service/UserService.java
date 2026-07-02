package com.ejada.e_commerce.user_service.service;

import com.ejada.e_commerce.common.exception.UserNotFoundException;
import com.ejada.e_commerce.user_service.model.User;
import com.ejada.e_commerce.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean canHaveWallet(Long id) {
        return userRepository.findById(id).map(user ->
                user.getRole().equalsIgnoreCase("customer") || user.getRole().equalsIgnoreCase("seller")
        ).orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    // role : Admin
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void updateUser(Long id, User updatedUser) {
        userRepository.findById(id).ifPresentOrElse((User existingUser) -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setRole(updatedUser.getRole());
                    userRepository.save(existingUser);
                },
                () -> {
                    throw new UserNotFoundException("User not found");
                }
        );
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(
                (User user) -> userRepository.deleteById(id),
                () -> {
                    throw new UserNotFoundException("User not found");
                }
        );
    }
}
