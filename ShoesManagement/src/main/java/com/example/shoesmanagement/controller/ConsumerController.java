package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.controller.helper.MappingHelper;
import com.example.shoesmanagement.dto.request.CreateConsumerRequest;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.util.Validator;
import com.example.shoesmanagement.service.ConsumerService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static com.example.shoesmanagement.model.util.ModelConstant.USER_EXISTED;

@CrossOrigin("*")
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
        Validator.checkExistingObject(consumerByUsername, String.format(USER_EXISTED, request.getUsername()));
        Validator.validateEmail(request.getEmail());
        Validator.checkMatchObject(request.getPassword(), request.getConfirmed());
        Consumer consumer = mappingHelper.mapConsumer(request);
        consumerService.saveConsumer(consumer);
        return new ShowDataResponse<>(consumer);
    }

    @GetMapping()
    public ShowDataResponse<?> getAll(
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Page<Consumer> allConsumer = consumerService.getAllConsumer(role, name, page, size);
        return new ShowDataResponse<>(allConsumer);
    }

    @GetMapping(path = "/users")
    public ShowDataResponse<?> getAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Page<Consumer> allConsumer = consumerService.getAllConsumerByUserRole(search, start, end, page, size);
        return new ShowDataResponse<>(allConsumer);
    }
}
