package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.controller.helper.MappingHelper;
import com.example.shoesmanagement.dto.request.CreateConsumerRequest;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.util.Validator;
import com.example.shoesmanagement.service.ConsumerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private final MappingHelper mappingHelper;
    private final ConsumerService consumerService;

    public ConsumerController(MappingHelper mappingHelper, ConsumerService consumerService) {
        this.mappingHelper = mappingHelper;
        this.consumerService = consumerService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signin")
    public ShowDataResponse<?> signin(@RequestParam("username") String userName,
                                      @RequestParam("password") String password) {
        return new ShowDataResponse<>(consumerService.signin(userName, password));
    }

    @PostMapping()
    public ShowDataResponse<?> createConsumer(@RequestBody CreateConsumerRequest request) {
        final Consumer consumerByUsername = consumerService.getConsumerByUsername(request.getUsername());
        Validator.checkExistingUser(consumerByUsername, request.getUsername());
        Validator.validateEmail(request.getEmail());
        Validator.checkMatchObject(request.getPassword(), request.getConfirmed());
        Consumer consumer = mappingHelper.mapConsumer(request);
        consumerService.saveConsumer(consumer);
        return new ShowDataResponse<>(consumer);
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
