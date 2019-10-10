package com.wmte.service;

import com.wmte.service.dto.EmployeeDepartmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.EmployeeDepartment}.
 */
public interface EmployeeDepartmentService {

    /**
     * Save a employeeDepartment.
     *
     * @param employeeDepartmentDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeeDepartmentDTO save(EmployeeDepartmentDTO employeeDepartmentDTO);

    /**
     * Get all the employeeDepartments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeDepartmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" employeeDepartment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeDepartmentDTO> findOne(Long id);

    /**
     * Delete the "id" employeeDepartment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
