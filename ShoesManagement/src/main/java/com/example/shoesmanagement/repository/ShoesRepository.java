package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.Shoes;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShoesRepository extends PagingAndSortingRepository<Shoes, Long>, JpaSpecificationExecutor<Shoes> {
}
