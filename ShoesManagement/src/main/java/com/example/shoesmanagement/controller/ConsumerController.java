package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.controller.helper.MappingHelper;
import com.example.shoesmanagement.dto.request.CreateConsumerRequest;
import com.example.shoesmanagement.dto.request.UpdateConsumerRequest;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.enums.Role;
import com.example.shoesmanagement.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @RequestMapping(method = RequestMethod.POST, path = "/signin")
    public ShowDataResponse<?> signin(
            @RequestParam("username") String userName, @RequestParam("password") String password
    ){
        return new ShowDataResponse<>(consumerService.signin(userName, password));
    }
    @PostMapping()
    public ShowDataResponse<?> createConsumer(
            @RequestBody CreateConsumerRequest createConsumerRequest
            ){
        Consumer consumer = MappingHelper.mapConsumer(createConsumerRequest);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_GUEST);
        consumer.setRoles(roles);
        return new ShowDataResponse<>(consumerService.signup(consumer));
    }
//    @PutMapping("/{id}")
//    public ShowDataResponse<?> updateConsumer(
//            @PathVariable("id") Long id, @RequestBody UpdateConsumerRequest updateConsumerRequest
//    ){
//        Consumer consumer = MappingHelper.mapConsumer(consumerService.get);
//        List<Role> roles = new ArrayList<>();
//        roles.add(Role.ROLE_GUEST);
//        consumer.setRoles(roles);
//        return new ShowDataResponse<>(consumerService.signup(consumer));
//    }

}
