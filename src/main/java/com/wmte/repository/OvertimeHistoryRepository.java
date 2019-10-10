package com.wmte.repository;
import com.wmte.domain.OvertimeHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OvertimeHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OvertimeHistoryRepository extends JpaRepository<OvertimeHistory, Long> {

}
