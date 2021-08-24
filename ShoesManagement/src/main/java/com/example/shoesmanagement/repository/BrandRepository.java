package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.Brand;
import com.example.shoesmanagement.model.enums.AppStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
    Brand findOneById(Long id);
    Brand findOneByIdAndStatus(Long id, AppStatus status);

    boolean existsByName(String name);

    List<Brand> findAllByStatus(AppStatus status, Sort by);
}
