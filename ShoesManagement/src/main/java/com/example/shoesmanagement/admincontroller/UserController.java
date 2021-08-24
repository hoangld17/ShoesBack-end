package com.example.shoesmanagement.admincontroller;

import com.example.shoesmanagement.admindto.response.AdminUserResponse;
import com.example.shoesmanagement.adminservice.UserService;
import com.example.shoesmanagement.controller.helper.MappingHelper;
import com.example.shoesmanagement.dto.request.CreateConsumerRequest;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.util.Validator;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.example.shoesmanagement.model.util.ModelConstant.USER_EXISTED;


@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class UserController {

    private final MappingHelper mappingHelper;
    private final UserService userService;

    public UserController(MappingHelper mappingHelper, UserService userService) {
        this.mappingHelper = mappingHelper;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ShowDataResponse<?> login(@RequestParam("username") String userName,
                                     @RequestParam("password") String password) {
        return new ShowDataResponse<>(userService.login(userName, password));
    }

    @PostMapping()
    public ShowDataResponse<?> createConsumer(@RequestBody CreateConsumerRequest request) throws IOException {
        final Consumer consumerByUsername = userService.getConsumerByUsername(request.getUsername());
        Validator.checkExistingObject(consumerByUsername, String.format(USER_EXISTED, request.getUsername()));
        Validator.validateEmail(request.getEmail());
        Validator.checkMatchObject(request.getPassword(), request.getConfirmed());


        Consumer consumer = mappingHelper.mapConsumer(request);
        userService.saveConsumer(consumer);
        return new ShowDataResponse<>(consumer);
    }

    @GetMapping(path = "/admin")
    public ShowDataResponse<?> getAllAdmin(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortedField") String sortedField,
            @RequestParam("sort") boolean sort
    ) {
        Page<AdminUserResponse> allConsumer = userService.getAdmin(search, page, size, sort, sortedField);
        return new ShowDataResponse<>(allConsumer);
    }

    @GetMapping(path = "/users")
    public ShowDataResponse<?> getAllUsers(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sortedField", required = false, defaultValue = "") String sortedField,
            @RequestParam(value = "sort", required = false) boolean sort
    ) {
        Page<AdminUserResponse> allConsumer = userService.getUsers(search, page, size, sort, sortedField);
        return new ShowDataResponse<>(allConsumer);
    }

}
