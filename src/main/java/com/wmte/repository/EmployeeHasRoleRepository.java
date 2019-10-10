package com.wmte.repository;
import com.wmte.domain.EmployeeHasRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeHasRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeHasRoleRepository extends JpaRepository<EmployeeHasRole, Long> {

}
