package com.example.shoesmanagement.repository.specification;

import com.example.shoesmanagement.model.Brand;
import com.example.shoesmanagement.model.enums.AppStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BrandSpecification {
    public Specification<Brand> doFilterBrand(
            String name,
            String search,
            boolean sort,
            String sortField

    ) {
        return (
                Root<Brand> clazzRoot, CriteriaQuery<?> cq, CriteriaBuilder cb
        ) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(clazzRoot.get("status"), AppStatus.ACTIVE));


            if (name != null && !name.trim().isEmpty()) {
                predicates.add(cb.equal(clazzRoot.get("name"), name));
            }
            if (search != null && !search.trim().isEmpty()) {
                String searchNew = search.trim();
                predicates.add(cb.or(
                        cb.like(clazzRoot.get("name"), "%" + searchNew + "%")));

            }

            Path orderClause;
            switch (sortField.trim()) {
                case "name":
                    orderClause = clazzRoot.get("name");
                    break;
                default:
                    orderClause = clazzRoot.get("createdDate");
                    break;
            }

            if (sort) {
                cq.orderBy(cb.asc(orderClause));
            } else {
                cq.orderBy(cb.desc(orderClause));
            }

            return cb.and(predicates.toArray(new Predicate[]{}));

        };
    }
}
