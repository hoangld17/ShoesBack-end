package com.example.shoesmanagement.controller.helper;

import com.example.shoesmanagement.dto.request.CreateConsumerRequest;
import com.example.shoesmanagement.dto.request.CreateShoeRequest;
import com.example.shoesmanagement.dto.request.UpdateConsumerRequest;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.Shoe;
import com.example.shoesmanagement.model.enums.UserRole;
import com.example.shoesmanagement.model.util.AppUtil;
import com.example.shoesmanagement.model.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MappingHelper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static Consumer mapConsumer(Consumer consumer, UpdateConsumerRequest updateConsumerRequest) {
        consumer.setFirstName(updateConsumerRequest.getFirstName());
        consumer.setLastName(updateConsumerRequest.getLastName());
        consumer.setEmail(updateConsumerRequest.getEmail());
        consumer.setPhone(updateConsumerRequest.getPhone());
        consumer.setAddress(updateConsumerRequest.getAddress());
        return consumer;
    }

    public Consumer mapConsumer(CreateConsumerRequest createConsumerRequest) {
        Consumer consumer = new Consumer();
        consumer.setFirstName(createConsumerRequest.getFirstName());
        consumer.setLastName(createConsumerRequest.getLastName());
        consumer.setEmail(createConsumerRequest.getEmail());
        consumer.setPhone(createConsumerRequest.getPhone());
        consumer.setAddress(createConsumerRequest.getAddress());
        consumer.setUsername(createConsumerRequest.getUsername());
        String passwordSalt = AppUtil.generateSalt();
        consumer.setPasswordHash(passwordEncoder.encode(createConsumerRequest.getPassword().concat(passwordSalt)));
        consumer.setPasswordSalt(passwordSalt);
        consumer.setRole(UserRole.USER);
        return consumer;
    }

    public Shoe mapShoe(CreateShoeRequest request) {
        Shoe shoe = new Shoe();
        shoe.setName(request.getName());
        shoe.setBrand_id(request.getBrand_id());
        shoe.setPrice(request.getPrice());
        return shoe;
    }
}
