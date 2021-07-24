package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.Brand;
import com.example.shoesmanagement.model.enums.AppStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
    Brand findOneByIdAndStatus(Long id, AppStatus appStatus);

    List<Brand> findAllByStatus(AppStatus appStatus);
}
