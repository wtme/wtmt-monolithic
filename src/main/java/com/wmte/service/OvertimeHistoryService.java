package com.wmte.service;

import com.wmte.service.dto.OvertimeHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.OvertimeHistory}.
 */
public interface OvertimeHistoryService {

    /**
     * Save a overtimeHistory.
     *
     * @param overtimeHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    OvertimeHistoryDTO save(OvertimeHistoryDTO overtimeHistoryDTO);

    /**
     * Get all the overtimeHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OvertimeHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" overtimeHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OvertimeHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" overtimeHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
