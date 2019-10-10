package com.wmte.web.rest;

import com.wmte.service.EmployeeDepartmentService;
import com.wmte.web.rest.errors.BadRequestAlertException;
import com.wmte.service.dto.EmployeeDepartmentDTO;

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
 * REST controller for managing {@link com.wmte.domain.EmployeeDepartment}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeDepartmentResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeDepartmentResource.class);

    private static final String ENTITY_NAME = "employeeDepartment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeDepartmentService employeeDepartmentService;

    public EmployeeDepartmentResource(EmployeeDepartmentService employeeDepartmentService) {
        this.employeeDepartmentService = employeeDepartmentService;
    }

    /**
     * {@code POST  /employee-departments} : Create a new employeeDepartment.
     *
     * @param employeeDepartmentDTO the employeeDepartmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDepartmentDTO, or with status {@code 400 (Bad Request)} if the employeeDepartment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-departments")
    public ResponseEntity<EmployeeDepartmentDTO> createEmployeeDepartment(@RequestBody EmployeeDepartmentDTO employeeDepartmentDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeDepartment : {}", employeeDepartmentDTO);
        if (employeeDepartmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeDepartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeDepartmentDTO result = employeeDepartmentService.save(employeeDepartmentDTO);
        return ResponseEntity.created(new URI("/api/employee-departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-departments} : Updates an existing employeeDepartment.
     *
     * @param employeeDepartmentDTO the employeeDepartmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDepartmentDTO,
     * or with status {@code 400 (Bad Request)} if the employeeDepartmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDepartmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-departments")
    public ResponseEntity<EmployeeDepartmentDTO> updateEmployeeDepartment(@RequestBody EmployeeDepartmentDTO employeeDepartmentDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeDepartment : {}", employeeDepartmentDTO);
        if (employeeDepartmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeDepartmentDTO result = employeeDepartmentService.save(employeeDepartmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDepartmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-departments} : get all the employeeDepartments.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeDepartments in body.
     */
    @GetMapping("/employee-departments")
    public ResponseEntity<List<EmployeeDepartmentDTO>> getAllEmployeeDepartments(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeDepartments");
        Page<EmployeeDepartmentDTO> page = employeeDepartmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-departments/:id} : get the "id" employeeDepartment.
     *
     * @param id the id of the employeeDepartmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDepartmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-departments/{id}")
    public ResponseEntity<EmployeeDepartmentDTO> getEmployeeDepartment(@PathVariable Long id) {
        log.debug("REST request to get EmployeeDepartment : {}", id);
        Optional<EmployeeDepartmentDTO> employeeDepartmentDTO = employeeDepartmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDepartmentDTO);
    }

    /**
     * {@code DELETE  /employee-departments/:id} : delete the "id" employeeDepartment.
     *
     * @param id the id of the employeeDepartmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-departments/{id}")
    public ResponseEntity<Void> deleteEmployeeDepartment(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeDepartment : {}", id);
        employeeDepartmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
