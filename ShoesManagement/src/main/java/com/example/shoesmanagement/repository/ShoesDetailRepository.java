package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.ShoesDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShoesDetailRepository extends PagingAndSortingRepository<ShoesDetail, Long>, JpaSpecificationExecutor<ShoesDetail> {
}
