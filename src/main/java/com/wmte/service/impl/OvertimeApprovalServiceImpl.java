package com.wmte.service.impl;

import com.wmte.service.OvertimeApprovalService;
import com.wmte.domain.OvertimeApproval;
import com.wmte.repository.OvertimeApprovalRepository;
import com.wmte.service.dto.OvertimeApprovalDTO;
import com.wmte.service.mapper.OvertimeApprovalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OvertimeApproval}.
 */
@Service
@Transactional
public class OvertimeApprovalServiceImpl implements OvertimeApprovalService {

    private final Logger log = LoggerFactory.getLogger(OvertimeApprovalServiceImpl.class);

    private final OvertimeApprovalRepository overtimeApprovalRepository;

    private final OvertimeApprovalMapper overtimeApprovalMapper;

    public OvertimeApprovalServiceImpl(OvertimeApprovalRepository overtimeApprovalRepository, OvertimeApprovalMapper overtimeApprovalMapper) {
        this.overtimeApprovalRepository = overtimeApprovalRepository;
        this.overtimeApprovalMapper = overtimeApprovalMapper;
    }

    /**
     * Save a overtimeApproval.
     *
     * @param overtimeApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OvertimeApprovalDTO save(OvertimeApprovalDTO overtimeApprovalDTO) {
        log.debug("Request to save OvertimeApproval : {}", overtimeApprovalDTO);
        OvertimeApproval overtimeApproval = overtimeApprovalMapper.toEntity(overtimeApprovalDTO);
        overtimeApproval = overtimeApprovalRepository.save(overtimeApproval);
        return overtimeApprovalMapper.toDto(overtimeApproval);
    }

    /**
     * Get all the overtimeApprovals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OvertimeApprovalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OvertimeApprovals");
        return overtimeApprovalRepository.findAll(pageable)
            .map(overtimeApprovalMapper::toDto);
    }


    /**
     * Get one overtimeApproval by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OvertimeApprovalDTO> findOne(Long id) {
        log.debug("Request to get OvertimeApproval : {}", id);
        return overtimeApprovalRepository.findById(id)
            .map(overtimeApprovalMapper::toDto);
    }

    /**
     * Delete the overtimeApproval by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OvertimeApproval : {}", id);
        overtimeApprovalRepository.deleteById(id);
    }
}
