package com.ejada.e_commerce.user_service.service;

import com.ejada.e_commerce.user_service.exception.UserAlreadyExistsException;
import com.ejada.e_commerce.user_service.model.User;
import com.ejada.e_commerce.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RegisterService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    public Long register(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent((u) -> {
                    throw new UserAlreadyExistsException("User already exists");
                });
        user.setPassword(encryptionService.encryptPassword(user.getPassword()));
        return userRepository.save(user).getId();
    }
}
