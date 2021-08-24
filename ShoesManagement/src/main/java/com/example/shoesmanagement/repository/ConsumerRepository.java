package com.example.shoesmanagement.repository;

import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ConsumerRepository extends PagingAndSortingRepository<Consumer, Long>, JpaSpecificationExecutor<Consumer> {
    Consumer findByUsername(String userName);
    boolean existsByUsername(String username);

    List<Consumer> findAllByRole(UserRole role);
}
