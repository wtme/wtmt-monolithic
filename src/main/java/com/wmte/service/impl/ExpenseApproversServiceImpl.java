package com.wmte.service.impl;

import com.wmte.service.ExpenseApproversService;
import com.wmte.domain.ExpenseApprovers;
import com.wmte.repository.ExpenseApproversRepository;
import com.wmte.service.dto.ExpenseApproversDTO;
import com.wmte.service.mapper.ExpenseApproversMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExpenseApprovers}.
 */
@Service
@Transactional
public class ExpenseApproversServiceImpl implements ExpenseApproversService {

    private final Logger log = LoggerFactory.getLogger(ExpenseApproversServiceImpl.class);

    private final ExpenseApproversRepository expenseApproversRepository;

    private final ExpenseApproversMapper expenseApproversMapper;

    public ExpenseApproversServiceImpl(ExpenseApproversRepository expenseApproversRepository, ExpenseApproversMapper expenseApproversMapper) {
        this.expenseApproversRepository = expenseApproversRepository;
        this.expenseApproversMapper = expenseApproversMapper;
    }

    /**
     * Save a expenseApprovers.
     *
     * @param expenseApproversDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ExpenseApproversDTO save(ExpenseApproversDTO expenseApproversDTO) {
        log.debug("Request to save ExpenseApprovers : {}", expenseApproversDTO);
        ExpenseApprovers expenseApprovers = expenseApproversMapper.toEntity(expenseApproversDTO);
        expenseApprovers = expenseApproversRepository.save(expenseApprovers);
        return expenseApproversMapper.toDto(expenseApprovers);
    }

    /**
     * Get all the expenseApprovers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExpenseApproversDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExpenseApprovers");
        return expenseApproversRepository.findAll(pageable)
            .map(expenseApproversMapper::toDto);
    }


    /**
     * Get one expenseApprovers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExpenseApproversDTO> findOne(Long id) {
        log.debug("Request to get ExpenseApprovers : {}", id);
        return expenseApproversRepository.findById(id)
            .map(expenseApproversMapper::toDto);
    }

    /**
     * Delete the expenseApprovers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExpenseApprovers : {}", id);
        expenseApproversRepository.deleteById(id);
    }
}
