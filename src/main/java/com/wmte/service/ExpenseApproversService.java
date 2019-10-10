package com.wmte.service;

import com.wmte.service.dto.ExpenseApproversDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.ExpenseApprovers}.
 */
public interface ExpenseApproversService {

    /**
     * Save a expenseApprovers.
     *
     * @param expenseApproversDTO the entity to save.
     * @return the persisted entity.
     */
    ExpenseApproversDTO save(ExpenseApproversDTO expenseApproversDTO);

    /**
     * Get all the expenseApprovers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExpenseApproversDTO> findAll(Pageable pageable);


    /**
     * Get the "id" expenseApprovers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExpenseApproversDTO> findOne(Long id);

    /**
     * Delete the "id" expenseApprovers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
