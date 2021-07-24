package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.service.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoes")
public class ShoesController {
    @Autowired
    ShoesService shoesService;
}
