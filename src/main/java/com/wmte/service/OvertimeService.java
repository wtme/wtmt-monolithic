package com.wmte.service;

import com.wmte.service.dto.OvertimeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.Overtime}.
 */
public interface OvertimeService {

    /**
     * Save a overtime.
     *
     * @param overtimeDTO the entity to save.
     * @return the persisted entity.
     */
    OvertimeDTO save(OvertimeDTO overtimeDTO);

    /**
     * Get all the overtimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OvertimeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" overtime.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OvertimeDTO> findOne(Long id);

    /**
     * Delete the "id" overtime.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
