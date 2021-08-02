package com.example.shoesmanagement.repository.specification;

import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.model.enums.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ConsumerSpecification {
    public Specification<Consumer> doFilterConsumer(
            String role,
            String search
    ) {
        return (consumerRoot,
                cq,
                cb) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(consumerRoot.get("status"), AppStatus.ACTIVE));
            if (search != null && !search.trim().isEmpty()) {
                String searchNew = search.trim();
                predicates.add(cb.and(
                        cb.like(consumerRoot.get("username"), "%" + searchNew + "%")));
            }


            if (role != null && role.equals("user")) {
                predicates.add(cb.and(
                        cb.equal(consumerRoot.get("role"), UserRole.USER)));
            }
            if (role != null && role.equals("admin")) {
                predicates.add(cb.and(
                        cb.equal(consumerRoot.get("role"), UserRole.ADMIN)));
            }
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }

    public Specification<Consumer> doFilterCustomerUser(
            String search,
            Date start,
            Date end
    ) {
        return (consumerRoot,
                cq,
                cb) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(consumerRoot.get("status"), AppStatus.ACTIVE));
            predicates.add(cb.equal(consumerRoot.get("role"), UserRole.USER));
            if (search != null && !search.trim().isEmpty()) {
                String searchNew = search.trim();
                predicates.add(cb.and(
                        cb.like(consumerRoot.get("username"), "%" + searchNew + "%")));
            }
            if (start != null && end != null) {
                predicates.add(cb.and(cb.between(consumerRoot.get("createdDate"), start, end)));
            }
            cq.orderBy(cb.asc(consumerRoot.get("createdDate")));
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
