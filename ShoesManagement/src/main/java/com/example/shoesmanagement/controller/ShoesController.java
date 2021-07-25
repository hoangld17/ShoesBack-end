package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.controller.helper.MappingHelper;
import com.example.shoesmanagement.dto.request.CreateShoeRequest;
import com.example.shoesmanagement.dto.response.ShoeResponse;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Shoe;
import com.example.shoesmanagement.service.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.example.shoesmanagement.model.util.ModelConstant.SHOE_NOT_FOUND;

@RestController
@RequestMapping("/shoes")
public class ShoesController {
    @Autowired
    ShoesService shoesService;
    @PostMapping()
    public ShowDataResponse<?> createShoe(
            @RequestBody CreateShoeRequest createShoeRequest
    ) {
        return new ShowDataResponse<>(shoesService.saveShoe(MappingHelper.mapShoe(createShoeRequest), MappingHelper.mapShoeDetail(createShoeRequest)));
    }
    @GetMapping("/{id}")
    public ShowDataResponse<?> getShoeById(
            @PathVariable("id") Long id
    ) {
        return new ShowDataResponse<>(shoesService.getShoeById(id));
    }
    @GetMapping("/idBrand/{id}")
    public ShowDataResponse<?> getShoeByIdBrand(
            @PathVariable("id") Long id
    ) {
        return new ShowDataResponse<>(shoesService.getShoeByIdBrand(id));
    }
    @GetMapping("/list")
    public ShowDataResponse<?> getAllShoe(
    ) {
        return new ShowDataResponse<>(shoesService.getAllShoe());
    }
}
