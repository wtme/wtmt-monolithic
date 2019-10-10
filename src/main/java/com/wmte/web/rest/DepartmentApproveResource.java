package com.wmte.web.rest;

import com.wmte.service.DepartmentApproveService;
import com.wmte.web.rest.errors.BadRequestAlertException;
import com.wmte.service.dto.DepartmentApproveDTO;

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
 * REST controller for managing {@link com.wmte.domain.DepartmentApprove}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentApproveResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentApproveResource.class);

    private static final String ENTITY_NAME = "departmentApprove";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartmentApproveService departmentApproveService;

    public DepartmentApproveResource(DepartmentApproveService departmentApproveService) {
        this.departmentApproveService = departmentApproveService;
    }

    /**
     * {@code POST  /department-approves} : Create a new departmentApprove.
     *
     * @param departmentApproveDTO the departmentApproveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departmentApproveDTO, or with status {@code 400 (Bad Request)} if the departmentApprove has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/department-approves")
    public ResponseEntity<DepartmentApproveDTO> createDepartmentApprove(@RequestBody DepartmentApproveDTO departmentApproveDTO) throws URISyntaxException {
        log.debug("REST request to save DepartmentApprove : {}", departmentApproveDTO);
        if (departmentApproveDTO.getId() != null) {
            throw new BadRequestAlertException("A new departmentApprove cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartmentApproveDTO result = departmentApproveService.save(departmentApproveDTO);
        return ResponseEntity.created(new URI("/api/department-approves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /department-approves} : Updates an existing departmentApprove.
     *
     * @param departmentApproveDTO the departmentApproveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departmentApproveDTO,
     * or with status {@code 400 (Bad Request)} if the departmentApproveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departmentApproveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/department-approves")
    public ResponseEntity<DepartmentApproveDTO> updateDepartmentApprove(@RequestBody DepartmentApproveDTO departmentApproveDTO) throws URISyntaxException {
        log.debug("REST request to update DepartmentApprove : {}", departmentApproveDTO);
        if (departmentApproveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartmentApproveDTO result = departmentApproveService.save(departmentApproveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departmentApproveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /department-approves} : get all the departmentApproves.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departmentApproves in body.
     */
    @GetMapping("/department-approves")
    public ResponseEntity<List<DepartmentApproveDTO>> getAllDepartmentApproves(Pageable pageable) {
        log.debug("REST request to get a page of DepartmentApproves");
        Page<DepartmentApproveDTO> page = departmentApproveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /department-approves/:id} : get the "id" departmentApprove.
     *
     * @param id the id of the departmentApproveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departmentApproveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/department-approves/{id}")
    public ResponseEntity<DepartmentApproveDTO> getDepartmentApprove(@PathVariable Long id) {
        log.debug("REST request to get DepartmentApprove : {}", id);
        Optional<DepartmentApproveDTO> departmentApproveDTO = departmentApproveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departmentApproveDTO);
    }

    /**
     * {@code DELETE  /department-approves/:id} : delete the "id" departmentApprove.
     *
     * @param id the id of the departmentApproveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/department-approves/{id}")
    public ResponseEntity<Void> deleteDepartmentApprove(@PathVariable Long id) {
        log.debug("REST request to delete DepartmentApprove : {}", id);
        departmentApproveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
