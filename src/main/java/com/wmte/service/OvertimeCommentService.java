package com.wmte.service;

import com.wmte.service.dto.OvertimeCommentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.OvertimeComment}.
 */
public interface OvertimeCommentService {

    /**
     * Save a overtimeComment.
     *
     * @param overtimeCommentDTO the entity to save.
     * @return the persisted entity.
     */
    OvertimeCommentDTO save(OvertimeCommentDTO overtimeCommentDTO);

    /**
     * Get all the overtimeComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OvertimeCommentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" overtimeComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OvertimeCommentDTO> findOne(Long id);

    /**
     * Delete the "id" overtimeComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
