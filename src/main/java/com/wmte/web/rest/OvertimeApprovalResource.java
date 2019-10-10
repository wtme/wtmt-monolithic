package com.wmte.web.rest;

import com.wmte.service.OvertimeApprovalService;
import com.wmte.web.rest.errors.BadRequestAlertException;
import com.wmte.service.dto.OvertimeApprovalDTO;

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
 * REST controller for managing {@link com.wmte.domain.OvertimeApproval}.
 */
@RestController
@RequestMapping("/api")
public class OvertimeApprovalResource {

    private final Logger log = LoggerFactory.getLogger(OvertimeApprovalResource.class);

    private static final String ENTITY_NAME = "overtimeApproval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OvertimeApprovalService overtimeApprovalService;

    public OvertimeApprovalResource(OvertimeApprovalService overtimeApprovalService) {
        this.overtimeApprovalService = overtimeApprovalService;
    }

    /**
     * {@code POST  /overtime-approvals} : Create a new overtimeApproval.
     *
     * @param overtimeApprovalDTO the overtimeApprovalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overtimeApprovalDTO, or with status {@code 400 (Bad Request)} if the overtimeApproval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/overtime-approvals")
    public ResponseEntity<OvertimeApprovalDTO> createOvertimeApproval(@RequestBody OvertimeApprovalDTO overtimeApprovalDTO) throws URISyntaxException {
        log.debug("REST request to save OvertimeApproval : {}", overtimeApprovalDTO);
        if (overtimeApprovalDTO.getId() != null) {
            throw new BadRequestAlertException("A new overtimeApproval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OvertimeApprovalDTO result = overtimeApprovalService.save(overtimeApprovalDTO);
        return ResponseEntity.created(new URI("/api/overtime-approvals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /overtime-approvals} : Updates an existing overtimeApproval.
     *
     * @param overtimeApprovalDTO the overtimeApprovalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overtimeApprovalDTO,
     * or with status {@code 400 (Bad Request)} if the overtimeApprovalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overtimeApprovalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/overtime-approvals")
    public ResponseEntity<OvertimeApprovalDTO> updateOvertimeApproval(@RequestBody OvertimeApprovalDTO overtimeApprovalDTO) throws URISyntaxException {
        log.debug("REST request to update OvertimeApproval : {}", overtimeApprovalDTO);
        if (overtimeApprovalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OvertimeApprovalDTO result = overtimeApprovalService.save(overtimeApprovalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, overtimeApprovalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /overtime-approvals} : get all the overtimeApprovals.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overtimeApprovals in body.
     */
    @GetMapping("/overtime-approvals")
    public ResponseEntity<List<OvertimeApprovalDTO>> getAllOvertimeApprovals(Pageable pageable) {
        log.debug("REST request to get a page of OvertimeApprovals");
        Page<OvertimeApprovalDTO> page = overtimeApprovalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /overtime-approvals/:id} : get the "id" overtimeApproval.
     *
     * @param id the id of the overtimeApprovalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overtimeApprovalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/overtime-approvals/{id}")
    public ResponseEntity<OvertimeApprovalDTO> getOvertimeApproval(@PathVariable Long id) {
        log.debug("REST request to get OvertimeApproval : {}", id);
        Optional<OvertimeApprovalDTO> overtimeApprovalDTO = overtimeApprovalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(overtimeApprovalDTO);
    }

    /**
     * {@code DELETE  /overtime-approvals/:id} : delete the "id" overtimeApproval.
     *
     * @param id the id of the overtimeApprovalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/overtime-approvals/{id}")
    public ResponseEntity<Void> deleteOvertimeApproval(@PathVariable Long id) {
        log.debug("REST request to delete OvertimeApproval : {}", id);
        overtimeApprovalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
