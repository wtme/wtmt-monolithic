package com.wmte.repository;
import com.wmte.domain.EmployeeDepartment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeDepartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, Long> {

}
