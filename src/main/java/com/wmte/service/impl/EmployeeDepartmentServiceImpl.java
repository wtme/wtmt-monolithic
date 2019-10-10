package com.wmte.service.impl;

import com.wmte.service.EmployeeDepartmentService;
import com.wmte.domain.EmployeeDepartment;
import com.wmte.repository.EmployeeDepartmentRepository;
import com.wmte.service.dto.EmployeeDepartmentDTO;
import com.wmte.service.mapper.EmployeeDepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeDepartment}.
 */
@Service
@Transactional
public class EmployeeDepartmentServiceImpl implements EmployeeDepartmentService {

    private final Logger log = LoggerFactory.getLogger(EmployeeDepartmentServiceImpl.class);

    private final EmployeeDepartmentRepository employeeDepartmentRepository;

    private final EmployeeDepartmentMapper employeeDepartmentMapper;

    public EmployeeDepartmentServiceImpl(EmployeeDepartmentRepository employeeDepartmentRepository, EmployeeDepartmentMapper employeeDepartmentMapper) {
        this.employeeDepartmentRepository = employeeDepartmentRepository;
        this.employeeDepartmentMapper = employeeDepartmentMapper;
    }

    /**
     * Save a employeeDepartment.
     *
     * @param employeeDepartmentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmployeeDepartmentDTO save(EmployeeDepartmentDTO employeeDepartmentDTO) {
        log.debug("Request to save EmployeeDepartment : {}", employeeDepartmentDTO);
        EmployeeDepartment employeeDepartment = employeeDepartmentMapper.toEntity(employeeDepartmentDTO);
        employeeDepartment = employeeDepartmentRepository.save(employeeDepartment);
        return employeeDepartmentMapper.toDto(employeeDepartment);
    }

    /**
     * Get all the employeeDepartments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDepartmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeDepartments");
        return employeeDepartmentRepository.findAll(pageable)
            .map(employeeDepartmentMapper::toDto);
    }


    /**
     * Get one employeeDepartment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDepartmentDTO> findOne(Long id) {
        log.debug("Request to get EmployeeDepartment : {}", id);
        return employeeDepartmentRepository.findById(id)
            .map(employeeDepartmentMapper::toDto);
    }

    /**
     * Delete the employeeDepartment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeDepartment : {}", id);
        employeeDepartmentRepository.deleteById(id);
    }
}
