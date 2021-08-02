package com.example.shoesmanagement.repository.specification;

import com.example.shoesmanagement.model.Bill;
import com.example.shoesmanagement.model.BillDetail;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BillSpecification {
    public Specification<Bill> doFilterBillDetailByCart(
            String username
    ) {
        return (billRoot,
                cq,
                cb) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.and(cb.equal(billRoot.get("consumerUsername"), username.trim())));
            predicates.add(cb.and(cb.equal(billRoot.get("isCart"), true)));

            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }
    public Specification<Bill> doFilterPayedBills(
            String username
    ) {
        return (billRoot,
                cq,
                cb) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.and(cb.equal(billRoot.get("consumerUsername"), username.trim())));
            predicates.add(cb.and(cb.equal(billRoot.get("isCart"), false)));
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }


}
