package com.ejada.e_commerce.common.client;

import com.ejada.e_commerce.common.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", url = "http://localhost:8000")
public interface UserClient {
    @GetMapping("user/{id}")
    UserDTO getUserById(@PathVariable Long id);
}