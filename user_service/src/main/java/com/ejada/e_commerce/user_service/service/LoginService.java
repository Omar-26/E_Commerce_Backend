package com.ejada.e_commerce.user_service.service;

import com.ejada.e_commerce.common.exception.UserNotFoundException;
import com.ejada.e_commerce.user_service.exception.InvalidPasswordException;
import com.ejada.e_commerce.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;

    public LoginService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {
        return userRepository.findByEmail(email).map(user -> {
            if (encryptionService.verifyPassword(password, user.getPassword())) {
                return jwtService.generateJWT(user);
            }
            throw new InvalidPasswordException("Password is incorrect");
        }).orElseThrow(() -> new UserNotFoundException("User does not exist"));
    }
}
