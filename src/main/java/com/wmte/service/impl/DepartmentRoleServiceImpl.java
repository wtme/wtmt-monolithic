package com.wmte.service.impl;

import com.wmte.service.DepartmentRoleService;
import com.wmte.domain.DepartmentRole;
import com.wmte.repository.DepartmentRoleRepository;
import com.wmte.service.dto.DepartmentRoleDTO;
import com.wmte.service.mapper.DepartmentRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DepartmentRole}.
 */
@Service
@Transactional
public class DepartmentRoleServiceImpl implements DepartmentRoleService {

    private final Logger log = LoggerFactory.getLogger(DepartmentRoleServiceImpl.class);

    private final DepartmentRoleRepository departmentRoleRepository;

    private final DepartmentRoleMapper departmentRoleMapper;

    public DepartmentRoleServiceImpl(DepartmentRoleRepository departmentRoleRepository, DepartmentRoleMapper departmentRoleMapper) {
        this.departmentRoleRepository = departmentRoleRepository;
        this.departmentRoleMapper = departmentRoleMapper;
    }

    /**
     * Save a departmentRole.
     *
     * @param departmentRoleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DepartmentRoleDTO save(DepartmentRoleDTO departmentRoleDTO) {
        log.debug("Request to save DepartmentRole : {}", departmentRoleDTO);
        DepartmentRole departmentRole = departmentRoleMapper.toEntity(departmentRoleDTO);
        departmentRole = departmentRoleRepository.save(departmentRole);
        return departmentRoleMapper.toDto(departmentRole);
    }

    /**
     * Get all the departmentRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepartmentRoles");
        return departmentRoleRepository.findAll(pageable)
            .map(departmentRoleMapper::toDto);
    }


    /**
     * Get one departmentRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentRoleDTO> findOne(Long id) {
        log.debug("Request to get DepartmentRole : {}", id);
        return departmentRoleRepository.findById(id)
            .map(departmentRoleMapper::toDto);
    }

    /**
     * Delete the departmentRole by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DepartmentRole : {}", id);
        departmentRoleRepository.deleteById(id);
    }
}
