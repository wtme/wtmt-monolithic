package com.wmte.repository;
import com.wmte.domain.DepartmentRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepartmentRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRoleRepository extends JpaRepository<DepartmentRole, Long> {

}
