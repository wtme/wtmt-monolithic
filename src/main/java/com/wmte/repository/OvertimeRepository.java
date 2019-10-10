package com.wmte.repository;
import com.wmte.domain.Overtime;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Overtime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OvertimeRepository extends JpaRepository<Overtime, Long> {

}
