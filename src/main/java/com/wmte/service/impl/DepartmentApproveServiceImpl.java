package com.wmte.service.impl;

import com.wmte.service.DepartmentApproveService;
import com.wmte.domain.DepartmentApprove;
import com.wmte.repository.DepartmentApproveRepository;
import com.wmte.service.dto.DepartmentApproveDTO;
import com.wmte.service.mapper.DepartmentApproveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DepartmentApprove}.
 */
@Service
@Transactional
public class DepartmentApproveServiceImpl implements DepartmentApproveService {

    private final Logger log = LoggerFactory.getLogger(DepartmentApproveServiceImpl.class);

    private final DepartmentApproveRepository departmentApproveRepository;

    private final DepartmentApproveMapper departmentApproveMapper;

    public DepartmentApproveServiceImpl(DepartmentApproveRepository departmentApproveRepository, DepartmentApproveMapper departmentApproveMapper) {
        this.departmentApproveRepository = departmentApproveRepository;
        this.departmentApproveMapper = departmentApproveMapper;
    }

    /**
     * Save a departmentApprove.
     *
     * @param departmentApproveDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DepartmentApproveDTO save(DepartmentApproveDTO departmentApproveDTO) {
        log.debug("Request to save DepartmentApprove : {}", departmentApproveDTO);
        DepartmentApprove departmentApprove = departmentApproveMapper.toEntity(departmentApproveDTO);
        departmentApprove = departmentApproveRepository.save(departmentApprove);
        return departmentApproveMapper.toDto(departmentApprove);
    }

    /**
     * Get all the departmentApproves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentApproveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepartmentApproves");
        return departmentApproveRepository.findAll(pageable)
            .map(departmentApproveMapper::toDto);
    }


    /**
     * Get one departmentApprove by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentApproveDTO> findOne(Long id) {
        log.debug("Request to get DepartmentApprove : {}", id);
        return departmentApproveRepository.findById(id)
            .map(departmentApproveMapper::toDto);
    }

    /**
     * Delete the departmentApprove by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DepartmentApprove : {}", id);
        departmentApproveRepository.deleteById(id);
    }
}
