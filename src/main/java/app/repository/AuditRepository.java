package app.repository;

import app.auditing.Audit;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long>, JpaSpecificationExecutor<Audit> {

    default Page<Audit> findAllByCriteria(String operation, Long createdBy, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return findAll((Specification<Audit>) (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (operation != null) {
                predicates.add(criteriaBuilder.like(root.get("operation"), "%" + operation + "%"));
            }
            if (createdBy != null) {
                predicates.add(criteriaBuilder.equal(root.get("createdBy"), createdBy));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), LocalDateTime.of(startDate, LocalTime.MIN)));
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), LocalDateTime.of(endDate, LocalTime.MAX)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}