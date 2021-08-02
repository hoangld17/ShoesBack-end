package com.example.shoesmanagement.service;

import com.example.shoesmanagement.dto.response.LoginResponse;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.enums.UserRole;
import org.springframework.data.domain.Page;

public interface ConsumerService {

    LoginResponse signin(String username, String password);

    void saveConsumer(Consumer consumer);

    Consumer getConsumerByUsername(String username);

    Consumer getConsumerById(Long id);


    Page<Consumer> getAllConsumer(String role, String search, int page, int size);
    Page<Consumer> getAllConsumerByUserRole( String search,String start, String end, int page, int size);

}
