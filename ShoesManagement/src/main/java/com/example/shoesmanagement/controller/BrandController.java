package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.dto.request.CreateBrandRequest;
import com.example.shoesmanagement.dto.request.UpdateBrandRequest;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.model.Brand;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    BrandService brandService;

    @RequestMapping(method = RequestMethod.POST)
    public ShowDataResponse<?> createBrand(
            @Valid @RequestBody CreateBrandRequest createBrandRequest
    ) {
        Brand brand = new Brand();
        brand.setName(createBrandRequest.getName().trim());
        brand.setStatus(AppStatus.ACTIVE);
        brand = brandService.saveBrand(brand);
        return new ShowDataResponse<>(brand);
    }

    @PutMapping("/{id}")
    public ShowDataResponse<?> updateBrand(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateBrandRequest updateBrandRequest
    ) {
        Brand brand = brandService.getBrandById(id);
        if (updateBrandRequest.getName() != null && !updateBrandRequest.getName().isBlank()) {
            brand.setName(updateBrandRequest.getName());
            brand = brandService.saveBrand(brand);
        }
        return new ShowDataResponse<>(brand);
    }

    @GetMapping("/{id}")
    public ShowDataResponse<?> getBrandById(
            @PathVariable("id") Long id
    ) {
        return new ShowDataResponse<>(brandService.getBrandById(id));
    }

    @GetMapping("/paging")
    public ShowDataResponse<?> getPagingBrand(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "false") boolean sort,
            @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField
    ) {
        return new ShowDataResponse<>(brandService.getPagingBrand(name, search, page, size, sort, sortField));
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ShowDataResponse<?> getAllBrand(
    ) {
        List<Brand> brandList = brandService.getAllBrand();
        return new ShowDataResponse<>(brandList);
    }

    @DeleteMapping("/{id}")
    public ShowDataResponse<?> deleteBrand(
            @PathVariable("id") Long id
    ) {
        return new ShowDataResponse<>(brandService.deleteBrand(id));
    }
}
