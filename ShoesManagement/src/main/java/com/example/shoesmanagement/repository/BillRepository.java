package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.Bill;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {
}
