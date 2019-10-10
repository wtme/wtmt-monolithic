package com.wmte.web.rest;

import com.wmte.service.EmployeeHasRoleService;
import com.wmte.web.rest.errors.BadRequestAlertException;
import com.wmte.service.dto.EmployeeHasRoleDTO;

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
 * REST controller for managing {@link com.wmte.domain.EmployeeHasRole}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeHasRoleResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeHasRoleResource.class);

    private static final String ENTITY_NAME = "employeeHasRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeHasRoleService employeeHasRoleService;

    public EmployeeHasRoleResource(EmployeeHasRoleService employeeHasRoleService) {
        this.employeeHasRoleService = employeeHasRoleService;
    }

    /**
     * {@code POST  /employee-has-roles} : Create a new employeeHasRole.
     *
     * @param employeeHasRoleDTO the employeeHasRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeHasRoleDTO, or with status {@code 400 (Bad Request)} if the employeeHasRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-has-roles")
    public ResponseEntity<EmployeeHasRoleDTO> createEmployeeHasRole(@RequestBody EmployeeHasRoleDTO employeeHasRoleDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeHasRole : {}", employeeHasRoleDTO);
        if (employeeHasRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeHasRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeHasRoleDTO result = employeeHasRoleService.save(employeeHasRoleDTO);
        return ResponseEntity.created(new URI("/api/employee-has-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-has-roles} : Updates an existing employeeHasRole.
     *
     * @param employeeHasRoleDTO the employeeHasRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeHasRoleDTO,
     * or with status {@code 400 (Bad Request)} if the employeeHasRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeHasRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-has-roles")
    public ResponseEntity<EmployeeHasRoleDTO> updateEmployeeHasRole(@RequestBody EmployeeHasRoleDTO employeeHasRoleDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeHasRole : {}", employeeHasRoleDTO);
        if (employeeHasRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeHasRoleDTO result = employeeHasRoleService.save(employeeHasRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeHasRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-has-roles} : get all the employeeHasRoles.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeHasRoles in body.
     */
    @GetMapping("/employee-has-roles")
    public ResponseEntity<List<EmployeeHasRoleDTO>> getAllEmployeeHasRoles(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeHasRoles");
        Page<EmployeeHasRoleDTO> page = employeeHasRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-has-roles/:id} : get the "id" employeeHasRole.
     *
     * @param id the id of the employeeHasRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeHasRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-has-roles/{id}")
    public ResponseEntity<EmployeeHasRoleDTO> getEmployeeHasRole(@PathVariable Long id) {
        log.debug("REST request to get EmployeeHasRole : {}", id);
        Optional<EmployeeHasRoleDTO> employeeHasRoleDTO = employeeHasRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeHasRoleDTO);
    }

    /**
     * {@code DELETE  /employee-has-roles/:id} : delete the "id" employeeHasRole.
     *
     * @param id the id of the employeeHasRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-has-roles/{id}")
    public ResponseEntity<Void> deleteEmployeeHasRole(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeHasRole : {}", id);
        employeeHasRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
