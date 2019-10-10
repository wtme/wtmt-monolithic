package com.wmte.web.rest;

import com.wmte.service.DepartmentRoleService;
import com.wmte.web.rest.errors.BadRequestAlertException;
import com.wmte.service.dto.DepartmentRoleDTO;

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
 * REST controller for managing {@link com.wmte.domain.DepartmentRole}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentRoleResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentRoleResource.class);

    private static final String ENTITY_NAME = "departmentRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartmentRoleService departmentRoleService;

    public DepartmentRoleResource(DepartmentRoleService departmentRoleService) {
        this.departmentRoleService = departmentRoleService;
    }

    /**
     * {@code POST  /department-roles} : Create a new departmentRole.
     *
     * @param departmentRoleDTO the departmentRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departmentRoleDTO, or with status {@code 400 (Bad Request)} if the departmentRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/department-roles")
    public ResponseEntity<DepartmentRoleDTO> createDepartmentRole(@Valid @RequestBody DepartmentRoleDTO departmentRoleDTO) throws URISyntaxException {
        log.debug("REST request to save DepartmentRole : {}", departmentRoleDTO);
        if (departmentRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new departmentRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartmentRoleDTO result = departmentRoleService.save(departmentRoleDTO);
        return ResponseEntity.created(new URI("/api/department-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /department-roles} : Updates an existing departmentRole.
     *
     * @param departmentRoleDTO the departmentRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departmentRoleDTO,
     * or with status {@code 400 (Bad Request)} if the departmentRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departmentRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/department-roles")
    public ResponseEntity<DepartmentRoleDTO> updateDepartmentRole(@Valid @RequestBody DepartmentRoleDTO departmentRoleDTO) throws URISyntaxException {
        log.debug("REST request to update DepartmentRole : {}", departmentRoleDTO);
        if (departmentRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartmentRoleDTO result = departmentRoleService.save(departmentRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departmentRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /department-roles} : get all the departmentRoles.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departmentRoles in body.
     */
    @GetMapping("/department-roles")
    public ResponseEntity<List<DepartmentRoleDTO>> getAllDepartmentRoles(Pageable pageable) {
        log.debug("REST request to get a page of DepartmentRoles");
        Page<DepartmentRoleDTO> page = departmentRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /department-roles/:id} : get the "id" departmentRole.
     *
     * @param id the id of the departmentRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departmentRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/department-roles/{id}")
    public ResponseEntity<DepartmentRoleDTO> getDepartmentRole(@PathVariable Long id) {
        log.debug("REST request to get DepartmentRole : {}", id);
        Optional<DepartmentRoleDTO> departmentRoleDTO = departmentRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departmentRoleDTO);
    }

    /**
     * {@code DELETE  /department-roles/:id} : delete the "id" departmentRole.
     *
     * @param id the id of the departmentRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/department-roles/{id}")
    public ResponseEntity<Void> deleteDepartmentRole(@PathVariable Long id) {
        log.debug("REST request to delete DepartmentRole : {}", id);
        departmentRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
