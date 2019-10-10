package com.wmte.service.impl;

import com.wmte.service.EmployeeHasRoleService;
import com.wmte.domain.EmployeeHasRole;
import com.wmte.repository.EmployeeHasRoleRepository;
import com.wmte.service.dto.EmployeeHasRoleDTO;
import com.wmte.service.mapper.EmployeeHasRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeHasRole}.
 */
@Service
@Transactional
public class EmployeeHasRoleServiceImpl implements EmployeeHasRoleService {

    private final Logger log = LoggerFactory.getLogger(EmployeeHasRoleServiceImpl.class);

    private final EmployeeHasRoleRepository employeeHasRoleRepository;

    private final EmployeeHasRoleMapper employeeHasRoleMapper;

    public EmployeeHasRoleServiceImpl(EmployeeHasRoleRepository employeeHasRoleRepository, EmployeeHasRoleMapper employeeHasRoleMapper) {
        this.employeeHasRoleRepository = employeeHasRoleRepository;
        this.employeeHasRoleMapper = employeeHasRoleMapper;
    }

    /**
     * Save a employeeHasRole.
     *
     * @param employeeHasRoleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmployeeHasRoleDTO save(EmployeeHasRoleDTO employeeHasRoleDTO) {
        log.debug("Request to save EmployeeHasRole : {}", employeeHasRoleDTO);
        EmployeeHasRole employeeHasRole = employeeHasRoleMapper.toEntity(employeeHasRoleDTO);
        employeeHasRole = employeeHasRoleRepository.save(employeeHasRole);
        return employeeHasRoleMapper.toDto(employeeHasRole);
    }

    /**
     * Get all the employeeHasRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeHasRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeHasRoles");
        return employeeHasRoleRepository.findAll(pageable)
            .map(employeeHasRoleMapper::toDto);
    }


    /**
     * Get one employeeHasRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeHasRoleDTO> findOne(Long id) {
        log.debug("Request to get EmployeeHasRole : {}", id);
        return employeeHasRoleRepository.findById(id)
            .map(employeeHasRoleMapper::toDto);
    }

    /**
     * Delete the employeeHasRole by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeHasRole : {}", id);
        employeeHasRoleRepository.deleteById(id);
    }
}
