package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.admindto.response.AdminLoginResponse;
import com.example.shoesmanagement.dto.response.ConsumerResponse;
import com.example.shoesmanagement.dto.response.LoginResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.util.Validator;
import com.example.shoesmanagement.repository.ConsumerRepository;
import com.example.shoesmanagement.security.JwtTokenProvider;
import com.example.shoesmanagement.service.BillService;
import com.example.shoesmanagement.service.ConsumerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.shoesmanagement.model.util.ModelConstant.USER_NOT_FOUND;
import static com.example.shoesmanagement.model.util.SecurityConstant.TOKEN_PREFIX;

@Service
@Transactional
public class ConsumerServiceImp implements ConsumerService {
    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LoginResponse signin(String username, String password) {
        try {
            Consumer consumer = getConsumerByUsername(username);
            Validator.checkNotFound(consumer, String.format(USER_NOT_FOUND, username));
            final String passwordSalt = consumer.getPasswordSalt();
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(username,
                                    password + passwordSalt));

            final UserDetails principal = (UserDetails) authenticate.getPrincipal();
            String token = jwtTokenProvider.createToken(principal);
            LoginResponse consumerResponse = new LoginResponse();
            consumerResponse.setUsername(principal.getUsername());
            consumerResponse.setEmail(consumer.getEmail());
            consumerResponse.setAddress(consumer.getAddress());
            consumerResponse.setPhone(consumer.getPhone());
            consumerResponse.setFirstName(consumer.getFirstName());
            consumerResponse.setLastName(consumer.getLastName());
            consumerResponse.setToken(TOKEN_PREFIX + token);
            return consumerResponse;
        } catch (AuthenticationException e) {
            throw new ApplicationException("Invalid username or password!", HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public void saveConsumer(Consumer consumer) {

        consumerRepository.save(consumer);
    }


    @Override
    public Consumer getConsumerByUsername(String username) {
        return consumerRepository.findByUsername(username);
    }

}
