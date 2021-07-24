package com.example.shoesmanagement.service;

import com.example.shoesmanagement.dto.response.LoginResponse;
import com.example.shoesmanagement.model.Consumer;

public interface ConsumerService {
    LoginResponse signin(String username, String password);
    Consumer signup(Consumer consumer);
}
