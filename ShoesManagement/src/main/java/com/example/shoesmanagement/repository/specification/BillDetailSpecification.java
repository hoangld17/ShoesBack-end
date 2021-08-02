package com.example.shoesmanagement.repository.specification;

import com.example.shoesmanagement.model.BillDetail;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BillDetailSpecification {
    public Specification<BillDetail> doFilterBillDetail(
            String username
    ) {
        return (billDetailRoot,
                cq,
                cb) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.and(cb.equal(billDetailRoot.get("username"), username.trim())));
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }




}
