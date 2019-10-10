package com.wmte.web.rest;

import com.wmte.service.OvertimeCommentService;
import com.wmte.web.rest.errors.BadRequestAlertException;
import com.wmte.service.dto.OvertimeCommentDTO;

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
 * REST controller for managing {@link com.wmte.domain.OvertimeComment}.
 */
@RestController
@RequestMapping("/api")
public class OvertimeCommentResource {

    private final Logger log = LoggerFactory.getLogger(OvertimeCommentResource.class);

    private static final String ENTITY_NAME = "overtimeComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OvertimeCommentService overtimeCommentService;

    public OvertimeCommentResource(OvertimeCommentService overtimeCommentService) {
        this.overtimeCommentService = overtimeCommentService;
    }

    /**
     * {@code POST  /overtime-comments} : Create a new overtimeComment.
     *
     * @param overtimeCommentDTO the overtimeCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overtimeCommentDTO, or with status {@code 400 (Bad Request)} if the overtimeComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/overtime-comments")
    public ResponseEntity<OvertimeCommentDTO> createOvertimeComment(@RequestBody OvertimeCommentDTO overtimeCommentDTO) throws URISyntaxException {
        log.debug("REST request to save OvertimeComment : {}", overtimeCommentDTO);
        if (overtimeCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new overtimeComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OvertimeCommentDTO result = overtimeCommentService.save(overtimeCommentDTO);
        return ResponseEntity.created(new URI("/api/overtime-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /overtime-comments} : Updates an existing overtimeComment.
     *
     * @param overtimeCommentDTO the overtimeCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overtimeCommentDTO,
     * or with status {@code 400 (Bad Request)} if the overtimeCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overtimeCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/overtime-comments")
    public ResponseEntity<OvertimeCommentDTO> updateOvertimeComment(@RequestBody OvertimeCommentDTO overtimeCommentDTO) throws URISyntaxException {
        log.debug("REST request to update OvertimeComment : {}", overtimeCommentDTO);
        if (overtimeCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OvertimeCommentDTO result = overtimeCommentService.save(overtimeCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, overtimeCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /overtime-comments} : get all the overtimeComments.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overtimeComments in body.
     */
    @GetMapping("/overtime-comments")
    public ResponseEntity<List<OvertimeCommentDTO>> getAllOvertimeComments(Pageable pageable) {
        log.debug("REST request to get a page of OvertimeComments");
        Page<OvertimeCommentDTO> page = overtimeCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /overtime-comments/:id} : get the "id" overtimeComment.
     *
     * @param id the id of the overtimeCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overtimeCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/overtime-comments/{id}")
    public ResponseEntity<OvertimeCommentDTO> getOvertimeComment(@PathVariable Long id) {
        log.debug("REST request to get OvertimeComment : {}", id);
        Optional<OvertimeCommentDTO> overtimeCommentDTO = overtimeCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(overtimeCommentDTO);
    }

    /**
     * {@code DELETE  /overtime-comments/:id} : delete the "id" overtimeComment.
     *
     * @param id the id of the overtimeCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/overtime-comments/{id}")
    public ResponseEntity<Void> deleteOvertimeComment(@PathVariable Long id) {
        log.debug("REST request to delete OvertimeComment : {}", id);
        overtimeCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
