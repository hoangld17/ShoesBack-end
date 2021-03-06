package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.BillDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BillDetailRepository extends PagingAndSortingRepository<BillDetail, Long>, JpaSpecificationExecutor<BillDetail> {
}
