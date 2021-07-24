package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.dto.response.ConsumerResponse;
import com.example.shoesmanagement.dto.response.LoginResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.repository.ConsumerRepository;
import com.example.shoesmanagement.security.JwtTokenProvider;
import com.example.shoesmanagement.service.ConsumerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConsumerServiceImp implements ConsumerService {
    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LoginResponse signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Consumer consumer = consumerRepository.findByUsername(username);
            String token = jwtTokenProvider.createToken(username, consumer.getRoles());
            LoginResponse consumerResponse = modelMapper.map(consumer, LoginResponse.class);
            consumerResponse.setToken(token);
            return consumerResponse;
        } catch (AuthenticationException e) {
            throw new ApplicationException("Invalid username or password!", HttpStatus.OK);
        }
    }

    @Override
    public Consumer signup(Consumer consumer) {
        if (!consumerRepository.existsByUsername(consumer.getUsername())) {
            consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
            return consumerRepository.save(consumer);
        } else {
            throw new ApplicationException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public List<ConsumerResponse> getAllConsumers() {
        List<Consumer> list = (List<Consumer>) consumerRepository.findAll();
        return list.stream().map(x -> modelMapper.map(x, ConsumerResponse.class)).collect(Collectors.toList());
    }

    public Consumer whoami(HttpServletRequest req) {
        return consumerRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public Consumer get(Long id) {
        return consumerRepository.findById(id).orElseThrow(() -> new ApplicationException("Account does not exist!"));
    }

    public ConsumerResponse changePassword(Long id, String oldPassword, String newPassword) {
        Consumer consumer = get(id);
        if (consumer.getPassword().equals(passwordEncoder.encode(oldPassword)))
            throw new ApplicationException("Old password is not correct!");
        consumer.setPassword(passwordEncoder.encode(newPassword));
        consumer = consumerRepository.save(consumer);
        return modelMapper.map(consumer, ConsumerResponse.class);
    }
}
