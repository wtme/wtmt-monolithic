package com.wmte.service;

import com.wmte.service.dto.DepartmentApproveDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.DepartmentApprove}.
 */
public interface DepartmentApproveService {

    /**
     * Save a departmentApprove.
     *
     * @param departmentApproveDTO the entity to save.
     * @return the persisted entity.
     */
    DepartmentApproveDTO save(DepartmentApproveDTO departmentApproveDTO);

    /**
     * Get all the departmentApproves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepartmentApproveDTO> findAll(Pageable pageable);


    /**
     * Get the "id" departmentApprove.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepartmentApproveDTO> findOne(Long id);

    /**
     * Delete the "id" departmentApprove.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
