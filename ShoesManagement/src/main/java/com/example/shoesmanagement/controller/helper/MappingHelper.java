package com.example.shoesmanagement.controller.helper;

import com.example.shoesmanagement.dto.request.CreateConsumerRequest;
import com.example.shoesmanagement.dto.request.UpdateConsumerRequest;
import com.example.shoesmanagement.model.Consumer;

public class MappingHelper {
    public static Consumer mapConsumer(CreateConsumerRequest createConsumerRequest){
        Consumer consumer = new Consumer();
        consumer.setFirstName(createConsumerRequest.getFirstName());
        consumer.setLastName(createConsumerRequest.getLastName());
        consumer.setEmail(createConsumerRequest.getEmail());
        consumer.setPhone(createConsumerRequest.getPhone());
        consumer.setAddress(createConsumerRequest.getAddress());
        consumer.setUsername(createConsumerRequest.getUsername());
        consumer.setPassword(createConsumerRequest.getPassword());
        return consumer;
    }
    public static Consumer mapConsumer(Consumer consumer, UpdateConsumerRequest updateConsumerRequest){
        consumer.setFirstName(updateConsumerRequest.getFirstName());
        consumer.setLastName(updateConsumerRequest.getLastName());
        consumer.setEmail(updateConsumerRequest.getEmail());
        consumer.setPhone(updateConsumerRequest.getPhone());
        consumer.setAddress(updateConsumerRequest.getAddress());
        return consumer;
    }
}
