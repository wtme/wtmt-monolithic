package com.wmte.service.impl;

import com.wmte.service.OvertimeHistoryService;
import com.wmte.domain.OvertimeHistory;
import com.wmte.repository.OvertimeHistoryRepository;
import com.wmte.service.dto.OvertimeHistoryDTO;
import com.wmte.service.mapper.OvertimeHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OvertimeHistory}.
 */
@Service
@Transactional
public class OvertimeHistoryServiceImpl implements OvertimeHistoryService {

    private final Logger log = LoggerFactory.getLogger(OvertimeHistoryServiceImpl.class);

    private final OvertimeHistoryRepository overtimeHistoryRepository;

    private final OvertimeHistoryMapper overtimeHistoryMapper;

    public OvertimeHistoryServiceImpl(OvertimeHistoryRepository overtimeHistoryRepository, OvertimeHistoryMapper overtimeHistoryMapper) {
        this.overtimeHistoryRepository = overtimeHistoryRepository;
        this.overtimeHistoryMapper = overtimeHistoryMapper;
    }

    /**
     * Save a overtimeHistory.
     *
     * @param overtimeHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OvertimeHistoryDTO save(OvertimeHistoryDTO overtimeHistoryDTO) {
        log.debug("Request to save OvertimeHistory : {}", overtimeHistoryDTO);
        OvertimeHistory overtimeHistory = overtimeHistoryMapper.toEntity(overtimeHistoryDTO);
        overtimeHistory = overtimeHistoryRepository.save(overtimeHistory);
        return overtimeHistoryMapper.toDto(overtimeHistory);
    }

    /**
     * Get all the overtimeHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OvertimeHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OvertimeHistories");
        return overtimeHistoryRepository.findAll(pageable)
            .map(overtimeHistoryMapper::toDto);
    }


    /**
     * Get one overtimeHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OvertimeHistoryDTO> findOne(Long id) {
        log.debug("Request to get OvertimeHistory : {}", id);
        return overtimeHistoryRepository.findById(id)
            .map(overtimeHistoryMapper::toDto);
    }

    /**
     * Delete the overtimeHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OvertimeHistory : {}", id);
        overtimeHistoryRepository.deleteById(id);
    }
}
