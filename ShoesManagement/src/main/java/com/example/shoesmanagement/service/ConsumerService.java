package com.example.shoesmanagement.service;

import com.example.shoesmanagement.admindto.response.AdminLoginResponse;
import com.example.shoesmanagement.dto.response.LoginResponse;
import com.example.shoesmanagement.model.Consumer;

public interface ConsumerService {
    LoginResponse signin(String username, String password);

    void saveConsumer(Consumer consumer);
    Consumer getConsumerByUsername(String username);

}
