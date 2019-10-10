package com.wmte.service.impl;

import com.wmte.service.OvertimeCommentService;
import com.wmte.domain.OvertimeComment;
import com.wmte.repository.OvertimeCommentRepository;
import com.wmte.service.dto.OvertimeCommentDTO;
import com.wmte.service.mapper.OvertimeCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OvertimeComment}.
 */
@Service
@Transactional
public class OvertimeCommentServiceImpl implements OvertimeCommentService {

    private final Logger log = LoggerFactory.getLogger(OvertimeCommentServiceImpl.class);

    private final OvertimeCommentRepository overtimeCommentRepository;

    private final OvertimeCommentMapper overtimeCommentMapper;

    public OvertimeCommentServiceImpl(OvertimeCommentRepository overtimeCommentRepository, OvertimeCommentMapper overtimeCommentMapper) {
        this.overtimeCommentRepository = overtimeCommentRepository;
        this.overtimeCommentMapper = overtimeCommentMapper;
    }

    /**
     * Save a overtimeComment.
     *
     * @param overtimeCommentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OvertimeCommentDTO save(OvertimeCommentDTO overtimeCommentDTO) {
        log.debug("Request to save OvertimeComment : {}", overtimeCommentDTO);
        OvertimeComment overtimeComment = overtimeCommentMapper.toEntity(overtimeCommentDTO);
        overtimeComment = overtimeCommentRepository.save(overtimeComment);
        return overtimeCommentMapper.toDto(overtimeComment);
    }

    /**
     * Get all the overtimeComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OvertimeCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OvertimeComments");
        return overtimeCommentRepository.findAll(pageable)
            .map(overtimeCommentMapper::toDto);
    }


    /**
     * Get one overtimeComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OvertimeCommentDTO> findOne(Long id) {
        log.debug("Request to get OvertimeComment : {}", id);
        return overtimeCommentRepository.findById(id)
            .map(overtimeCommentMapper::toDto);
    }

    /**
     * Delete the overtimeComment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OvertimeComment : {}", id);
        overtimeCommentRepository.deleteById(id);
    }
}
