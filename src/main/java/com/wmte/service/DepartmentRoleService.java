package com.wmte.service;

import com.wmte.service.dto.DepartmentRoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmte.domain.DepartmentRole}.
 */
public interface DepartmentRoleService {

    /**
     * Save a departmentRole.
     *
     * @param departmentRoleDTO the entity to save.
     * @return the persisted entity.
     */
    DepartmentRoleDTO save(DepartmentRoleDTO departmentRoleDTO);

    /**
     * Get all the departmentRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepartmentRoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" departmentRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepartmentRoleDTO> findOne(Long id);

    /**
     * Delete the "id" departmentRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
