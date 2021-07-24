package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.Shoe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShoesRepository extends
        PagingAndSortingRepository<Shoe, Long>,
        JpaSpecificationExecutor<Shoe> {
    Shoe findShoeById(Long id);
}
