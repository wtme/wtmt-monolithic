package com.wmte.web.rest;

import com.wmte.service.ExpenseApproversService;
import com.wmte.web.rest.errors.BadRequestAlertException;
import com.wmte.service.dto.ExpenseApproversDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.wmte.domain.ExpenseApprovers}.
 */
@RestController
@RequestMapping("/api")
public class ExpenseApproversResource {

    private final Logger log = LoggerFactory.getLogger(ExpenseApproversResource.class);

    private static final String ENTITY_NAME = "expenseApprovers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpenseApproversService expenseApproversService;

    public ExpenseApproversResource(ExpenseApproversService expenseApproversService) {
        this.expenseApproversService = expenseApproversService;
    }

    /**
     * {@code POST  /expense-approvers} : Create a new expenseApprovers.
     *
     * @param expenseApproversDTO the expenseApproversDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expenseApproversDTO, or with status {@code 400 (Bad Request)} if the expenseApprovers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expense-approvers")
    public ResponseEntity<ExpenseApproversDTO> createExpenseApprovers(@RequestBody ExpenseApproversDTO expenseApproversDTO) throws URISyntaxException {
        log.debug("REST request to save ExpenseApprovers : {}", expenseApproversDTO);
        if (expenseApproversDTO.getId() != null) {
            throw new BadRequestAlertException("A new expenseApprovers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpenseApproversDTO result = expenseApproversService.save(expenseApproversDTO);
        return ResponseEntity.created(new URI("/api/expense-approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expense-approvers} : Updates an existing expenseApprovers.
     *
     * @param expenseApproversDTO the expenseApproversDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenseApproversDTO,
     * or with status {@code 400 (Bad Request)} if the expenseApproversDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expenseApproversDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expense-approvers")
    public ResponseEntity<ExpenseApproversDTO> updateExpenseApprovers(@RequestBody ExpenseApproversDTO expenseApproversDTO) throws URISyntaxException {
        log.debug("REST request to update ExpenseApprovers : {}", expenseApproversDTO);
        if (expenseApproversDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpenseApproversDTO result = expenseApproversService.save(expenseApproversDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenseApproversDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expense-approvers} : get all the expenseApprovers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expenseApprovers in body.
     */
    @GetMapping("/expense-approvers")
    public ResponseEntity<List<ExpenseApproversDTO>> getAllExpenseApprovers(Pageable pageable) {
        log.debug("REST request to get a page of ExpenseApprovers");
        Page<ExpenseApproversDTO> page = expenseApproversService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expense-approvers/:id} : get the "id" expenseApprovers.
     *
     * @param id the id of the expenseApproversDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expenseApproversDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expense-approvers/{id}")
    public ResponseEntity<ExpenseApproversDTO> getExpenseApprovers(@PathVariable Long id) {
        log.debug("REST request to get ExpenseApprovers : {}", id);
        Optional<ExpenseApproversDTO> expenseApproversDTO = expenseApproversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expenseApproversDTO);
    }

    /**
     * {@code DELETE  /expense-approvers/:id} : delete the "id" expenseApprovers.
     *
     * @param id the id of the expenseApproversDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expense-approvers/{id}")
    public ResponseEntity<Void> deleteExpenseApprovers(@PathVariable Long id) {
        log.debug("REST request to delete ExpenseApprovers : {}", id);
        expenseApproversService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
