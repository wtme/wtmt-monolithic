package com.wmte.service;

import com.wmte.service.dto.OvertimeApprovalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.OvertimeApproval}.
 */
public interface OvertimeApprovalService {

    /**
     * Save a overtimeApproval.
     *
     * @param overtimeApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    OvertimeApprovalDTO save(OvertimeApprovalDTO overtimeApprovalDTO);

    /**
     * Get all the overtimeApprovals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OvertimeApprovalDTO> findAll(Pageable pageable);


    /**
     * Get the "id" overtimeApproval.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OvertimeApprovalDTO> findOne(Long id);

    /**
     * Delete the "id" overtimeApproval.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
