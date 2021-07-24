package com.example.shoesmanagement;

import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.enums.Role;
import com.example.shoesmanagement.service.ConsumerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ShoesManagementApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ShoesManagementApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    ConsumerService consumerService;
    @Override
    public void run(String... args) throws Exception {
        Consumer consumer = new Consumer();
        consumer.setUsername("hoang");
        consumer.setPassword("123");
        List<Role> list = new ArrayList<>();
        list.add(Role.ROLE_ADMIN);
        consumer.setRoles(list);
        consumerService.signup(consumer);
    }
}
