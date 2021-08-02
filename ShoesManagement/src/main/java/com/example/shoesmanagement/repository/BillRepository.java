package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.Bill;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {
    Bill findOneById(Long id);

    List<Bill> findAllByConsumerUsername(String username);

}
