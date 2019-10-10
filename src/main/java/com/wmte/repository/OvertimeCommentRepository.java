package com.wmte.repository;
import com.wmte.domain.OvertimeComment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OvertimeComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OvertimeCommentRepository extends JpaRepository<OvertimeComment, Long> {

}
