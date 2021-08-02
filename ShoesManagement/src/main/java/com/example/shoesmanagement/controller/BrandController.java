package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.dto.request.CreateBrandRequest;
import com.example.shoesmanagement.dto.request.UpdateBrandRequest;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.model.Brand;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.model.util.Validator;
import com.example.shoesmanagement.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.shoesmanagement.model.util.ModelConstant.BRAND_NOT_FOUND;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    BrandService brandService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ShowDataResponse<?> createBrand(
            @Valid @RequestBody CreateBrandRequest createBrandRequest
    ) {
        Brand brand = new Brand();
        brand.setName(createBrandRequest.getName().trim());
        brand.setEmail(createBrandRequest.getEmail());
        brand.setPhone(createBrandRequest.getPhone());
        brand.setImgUrl(createBrandRequest.getImgUrl());
        brand.setStatus(AppStatus.ACTIVE);
        brand = brandService.saveBrand(brand);
        return new ShowDataResponse<>(brand);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ShowDataResponse<?> updateBrand(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateBrandRequest updateBrandRequest) {
        Brand brand = brandService.getBrandById(id);
        if (updateBrandRequest.getName() != null && !updateBrandRequest.getName().isBlank()) {
            brand.setName(updateBrandRequest.getName());
        }
        if (updateBrandRequest.getPhone() != null && !updateBrandRequest.getPhone().isBlank()) {
            brand.setPhone(updateBrandRequest.getPhone());
        }
        if (updateBrandRequest.getEmail() != null && !updateBrandRequest.getEmail().isBlank()) {
            brand.setEmail(updateBrandRequest.getEmail());
        }
        if (updateBrandRequest.getImgUrl() != null && !updateBrandRequest.getImgUrl().isBlank()) {
            brand.setImgUrl(updateBrandRequest.getImgUrl());
        }
        brandService.saveBrand(brand);
        return new ShowDataResponse<>(brand);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ShowDataResponse<?> getBrandById(
            @PathVariable("id") Long id) {
        Brand brand = brandService.getBrandById(id);
        Validator.checkNotFound(brand, String.format(BRAND_NOT_FOUND, id.toString()));
        return new ShowDataResponse<>(brand);
    }

    @CrossOrigin()
    @GetMapping("/paging")
    public ShowDataResponse<?> getPagingBrand(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "true") boolean sort,
            @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField
    ) {
        return new ShowDataResponse<>(brandService.getPagingBrand(name, search, page, size, sort, sortField));
    }

    @CrossOrigin()
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ShowDataResponse<?> getAllBrand(
    ) {
        List<Brand> brandList = brandService.getAllBrand();
        return new ShowDataResponse<>(brandList);
    }

    @CrossOrigin()
    @DeleteMapping("/{id}")
    public ShowDataResponse<?> deleteBrand(
            @PathVariable("id") Long id) {
        Brand brand = brandService.getBrandById(id);
        System.out.println(brand.getName());
        Validator.checkNotFound(brand, String.format(BRAND_NOT_FOUND, id.toString()));
        return new ShowDataResponse<>(brandService.deleteBrand(brand));
    }
}
