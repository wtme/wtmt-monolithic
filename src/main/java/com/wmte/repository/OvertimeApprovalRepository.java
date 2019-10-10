package com.wmte.repository;
import com.wmte.domain.OvertimeApproval;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OvertimeApproval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OvertimeApprovalRepository extends JpaRepository<OvertimeApproval, Long> {

}
