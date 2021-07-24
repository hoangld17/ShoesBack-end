package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.controller.helper.MappingHelper;
import com.example.shoesmanagement.dto.request.CreateShoeRequest;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Shoe;
import com.example.shoesmanagement.service.ShoesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.example.shoesmanagement.model.util.ModelConstant.SHOE_NOT_FOUND;

@RestController
@RequestMapping("/shoes")
public class ShoesController {

    private final ShoesService shoesService;
    private final MappingHelper mappingHelper;

    public ShoesController(ShoesService shoesService, MappingHelper mappingHelper) {
        this.shoesService = shoesService;
        this.mappingHelper = mappingHelper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ShowDataResponse<List<Shoe>> getAll() {
        Collection<Shoe> shoes = shoesService.getAllShoe();
        return new ShowDataResponse<>((List<Shoe>) shoes);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ShowDataResponse<Shoe> addShoe(@RequestBody CreateShoeRequest request) {
        final Shoe shoe = mappingHelper.mapShoe(request);
        shoesService.saveShoe(shoe);
        return new ShowDataResponse<>(shoe);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ShowDataResponse<Shoe> updateShoe(@PathVariable Long id, @RequestBody CreateShoeRequest request) {
        final Shoe shoeById = shoesService.getShoeById(id);
        if (shoeById == null)
            throw new ApplicationException(String.format(SHOE_NOT_FOUND, id), HttpStatus.NOT_FOUND);
        final Shoe shoe = mappingHelper.mapShoe(request);
        shoesService.saveShoe(shoe);
        return new ShowDataResponse<>(shoe);
    }
}
