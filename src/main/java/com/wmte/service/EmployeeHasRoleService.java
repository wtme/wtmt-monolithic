package com.wmte.service;

import com.wmte.service.dto.EmployeeHasRoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.EmployeeHasRole}.
 */
public interface EmployeeHasRoleService {

    /**
     * Save a employeeHasRole.
     *
     * @param employeeHasRoleDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeeHasRoleDTO save(EmployeeHasRoleDTO employeeHasRoleDTO);

    /**
     * Get all the employeeHasRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeHasRoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" employeeHasRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeHasRoleDTO> findOne(Long id);

    /**
     * Delete the "id" employeeHasRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
