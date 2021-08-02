package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Brand;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.repository.BrandRepository;
import com.example.shoesmanagement.repository.specification.BrandSpecification;
import com.example.shoesmanagement.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BrandServiceImp implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandSpecification brandSpecification;


    @Override
    public Brand saveBrand(Brand brand) {
//        if (brandRepository.existsByName(brand.getName()))
//            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Name of brand exists."+ brand.getName());
        return brandRepository.save(brand);
    }

    @Override
    public Brand getBrandById(Long id) {
        Brand brand = brandRepository.findOneByIdAndStatus(id, AppStatus.ACTIVE);
        if (brand == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, String.format("Brand name %s not found.", brand.getName()));
        return brand;
    }

    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAllByStatus(AppStatus.ACTIVE);
    }


    @Override
    public Brand deleteBrand(Brand brand) {
//        Brand brand = getBrandById(id);
//        if (studentService.findAllByBrandId(id).isEmpty()) {
        brand.setStatus(AppStatus.INACTIVE);
        this.brandRepository.save(brand);
//        } else throw new ApplicationException(APIStatus.BAD_REQUEST, "The brand can not delete!");
        return brand;
    }

    @Override
    public Page<Brand> getPagingBrand(String name, String search, int page, int size, boolean sort, String sortField) {
        Specification<Brand> specification = brandSpecification.doFilterBrand(name, search, sort, sortField);
        Pageable pageable = PageRequest.of(page - 1, size);
        return brandRepository.findAll(specification, pageable);
    }
}
