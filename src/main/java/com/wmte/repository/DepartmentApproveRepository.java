package com.wmte.repository;
import com.wmte.domain.DepartmentApprove;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepartmentApprove entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentApproveRepository extends JpaRepository<DepartmentApprove, Long> {

}
