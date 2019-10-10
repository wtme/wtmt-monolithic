package com.wmte.web.rest;

import com.wmte.service.OvertimeService;
import com.wmte.web.rest.errors.BadRequestAlertException;
import com.wmte.service.dto.OvertimeDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.wmte.domain.Overtime}.
 */
@RestController
@RequestMapping("/api")
public class OvertimeResource {

    private final Logger log = LoggerFactory.getLogger(OvertimeResource.class);

    private static final String ENTITY_NAME = "overtime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OvertimeService overtimeService;

    public OvertimeResource(OvertimeService overtimeService) {
        this.overtimeService = overtimeService;
    }

    /**
     * {@code POST  /overtimes} : Create a new overtime.
     *
     * @param overtimeDTO the overtimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overtimeDTO, or with status {@code 400 (Bad Request)} if the overtime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/overtimes")
    public ResponseEntity<OvertimeDTO> createOvertime(@Valid @RequestBody OvertimeDTO overtimeDTO) throws URISyntaxException {
        log.debug("REST request to save Overtime : {}", overtimeDTO);
        if (overtimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new overtime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OvertimeDTO result = overtimeService.save(overtimeDTO);
        return ResponseEntity.created(new URI("/api/overtimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /overtimes} : Updates an existing overtime.
     *
     * @param overtimeDTO the overtimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overtimeDTO,
     * or with status {@code 400 (Bad Request)} if the overtimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overtimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/overtimes")
    public ResponseEntity<OvertimeDTO> updateOvertime(@Valid @RequestBody OvertimeDTO overtimeDTO) throws URISyntaxException {
        log.debug("REST request to update Overtime : {}", overtimeDTO);
        if (overtimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OvertimeDTO result = overtimeService.save(overtimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, overtimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /overtimes} : get all the overtimes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overtimes in body.
     */
    @GetMapping("/overtimes")
    public ResponseEntity<List<OvertimeDTO>> getAllOvertimes(Pageable pageable) {
        log.debug("REST request to get a page of Overtimes");
        Page<OvertimeDTO> page = overtimeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /overtimes/:id} : get the "id" overtime.
     *
     * @param id the id of the overtimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overtimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/overtimes/{id}")
    public ResponseEntity<OvertimeDTO> getOvertime(@PathVariable Long id) {
        log.debug("REST request to get Overtime : {}", id);
        Optional<OvertimeDTO> overtimeDTO = overtimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(overtimeDTO);
    }

    /**
     * {@code DELETE  /overtimes/:id} : delete the "id" overtime.
     *
     * @param id the id of the overtimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/overtimes/{id}")
    public ResponseEntity<Void> deleteOvertime(@PathVariable Long id) {
        log.debug("REST request to delete Overtime : {}", id);
        overtimeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
