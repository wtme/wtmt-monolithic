package com.wmte.repository;
import com.wmte.domain.ExpenseApprovers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExpenseApprovers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpenseApproversRepository extends JpaRepository<ExpenseApprovers, Long> {

}
