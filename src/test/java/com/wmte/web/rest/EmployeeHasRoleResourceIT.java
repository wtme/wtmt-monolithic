package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.EmployeeHasRole;
import com.wmte.repository.EmployeeHasRoleRepository;
import com.wmte.service.EmployeeHasRoleService;
import com.wmte.service.dto.EmployeeHasRoleDTO;
import com.wmte.service.mapper.EmployeeHasRoleMapper;
import com.wmte.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wmte.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeHasRoleResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class EmployeeHasRoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EmployeeHasRoleRepository employeeHasRoleRepository;

    @Autowired
    private EmployeeHasRoleMapper employeeHasRoleMapper;

    @Autowired
    private EmployeeHasRoleService employeeHasRoleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEmployeeHasRoleMockMvc;

    private EmployeeHasRole employeeHasRole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeHasRoleResource employeeHasRoleResource = new EmployeeHasRoleResource(employeeHasRoleService);
        this.restEmployeeHasRoleMockMvc = MockMvcBuilders.standaloneSetup(employeeHasRoleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeHasRole createEntity(EntityManager em) {
        EmployeeHasRole employeeHasRole = new EmployeeHasRole()
            .name(DEFAULT_NAME);
        return employeeHasRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeHasRole createUpdatedEntity(EntityManager em) {
        EmployeeHasRole employeeHasRole = new EmployeeHasRole()
            .name(UPDATED_NAME);
        return employeeHasRole;
    }

    @BeforeEach
    public void initTest() {
        employeeHasRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeHasRole() throws Exception {
        int databaseSizeBeforeCreate = employeeHasRoleRepository.findAll().size();

        // Create the EmployeeHasRole
        EmployeeHasRoleDTO employeeHasRoleDTO = employeeHasRoleMapper.toDto(employeeHasRole);
        restEmployeeHasRoleMockMvc.perform(post("/api/employee-has-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeHasRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeHasRole in the database
        List<EmployeeHasRole> employeeHasRoleList = employeeHasRoleRepository.findAll();
        assertThat(employeeHasRoleList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeHasRole testEmployeeHasRole = employeeHasRoleList.get(employeeHasRoleList.size() - 1);
        assertThat(testEmployeeHasRole.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEmployeeHasRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeHasRoleRepository.findAll().size();

        // Create the EmployeeHasRole with an existing ID
        employeeHasRole.setId(1L);
        EmployeeHasRoleDTO employeeHasRoleDTO = employeeHasRoleMapper.toDto(employeeHasRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeHasRoleMockMvc.perform(post("/api/employee-has-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeHasRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeHasRole in the database
        List<EmployeeHasRole> employeeHasRoleList = employeeHasRoleRepository.findAll();
        assertThat(employeeHasRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeHasRoles() throws Exception {
        // Initialize the database
        employeeHasRoleRepository.saveAndFlush(employeeHasRole);

        // Get all the employeeHasRoleList
        restEmployeeHasRoleMockMvc.perform(get("/api/employee-has-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeHasRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeHasRole() throws Exception {
        // Initialize the database
        employeeHasRoleRepository.saveAndFlush(employeeHasRole);

        // Get the employeeHasRole
        restEmployeeHasRoleMockMvc.perform(get("/api/employee-has-roles/{id}", employeeHasRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeHasRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeHasRole() throws Exception {
        // Get the employeeHasRole
        restEmployeeHasRoleMockMvc.perform(get("/api/employee-has-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeHasRole() throws Exception {
        // Initialize the database
        employeeHasRoleRepository.saveAndFlush(employeeHasRole);

        int databaseSizeBeforeUpdate = employeeHasRoleRepository.findAll().size();

        // Update the employeeHasRole
        EmployeeHasRole updatedEmployeeHasRole = employeeHasRoleRepository.findById(employeeHasRole.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeHasRole are not directly saved in db
        em.detach(updatedEmployeeHasRole);
        updatedEmployeeHasRole
            .name(UPDATED_NAME);
        EmployeeHasRoleDTO employeeHasRoleDTO = employeeHasRoleMapper.toDto(updatedEmployeeHasRole);

        restEmployeeHasRoleMockMvc.perform(put("/api/employee-has-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeHasRoleDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeHasRole in the database
        List<EmployeeHasRole> employeeHasRoleList = employeeHasRoleRepository.findAll();
        assertThat(employeeHasRoleList).hasSize(databaseSizeBeforeUpdate);
        EmployeeHasRole testEmployeeHasRole = employeeHasRoleList.get(employeeHasRoleList.size() - 1);
        assertThat(testEmployeeHasRole.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeHasRole() throws Exception {
        int databaseSizeBeforeUpdate = employeeHasRoleRepository.findAll().size();

        // Create the EmployeeHasRole
        EmployeeHasRoleDTO employeeHasRoleDTO = employeeHasRoleMapper.toDto(employeeHasRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeHasRoleMockMvc.perform(put("/api/employee-has-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeHasRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeHasRole in the database
        List<EmployeeHasRole> employeeHasRoleList = employeeHasRoleRepository.findAll();
        assertThat(employeeHasRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeHasRole() throws Exception {
        // Initialize the database
        employeeHasRoleRepository.saveAndFlush(employeeHasRole);

        int databaseSizeBeforeDelete = employeeHasRoleRepository.findAll().size();

        // Delete the employeeHasRole
        restEmployeeHasRoleMockMvc.perform(delete("/api/employee-has-roles/{id}", employeeHasRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeHasRole> employeeHasRoleList = employeeHasRoleRepository.findAll();
        assertThat(employeeHasRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeHasRole.class);
        EmployeeHasRole employeeHasRole1 = new EmployeeHasRole();
        employeeHasRole1.setId(1L);
        EmployeeHasRole employeeHasRole2 = new EmployeeHasRole();
        employeeHasRole2.setId(employeeHasRole1.getId());
        assertThat(employeeHasRole1).isEqualTo(employeeHasRole2);
        employeeHasRole2.setId(2L);
        assertThat(employeeHasRole1).isNotEqualTo(employeeHasRole2);
        employeeHasRole1.setId(null);
        assertThat(employeeHasRole1).isNotEqualTo(employeeHasRole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeHasRoleDTO.class);
        EmployeeHasRoleDTO employeeHasRoleDTO1 = new EmployeeHasRoleDTO();
        employeeHasRoleDTO1.setId(1L);
        EmployeeHasRoleDTO employeeHasRoleDTO2 = new EmployeeHasRoleDTO();
        assertThat(employeeHasRoleDTO1).isNotEqualTo(employeeHasRoleDTO2);
        employeeHasRoleDTO2.setId(employeeHasRoleDTO1.getId());
        assertThat(employeeHasRoleDTO1).isEqualTo(employeeHasRoleDTO2);
        employeeHasRoleDTO2.setId(2L);
        assertThat(employeeHasRoleDTO1).isNotEqualTo(employeeHasRoleDTO2);
        employeeHasRoleDTO1.setId(null);
        assertThat(employeeHasRoleDTO1).isNotEqualTo(employeeHasRoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeHasRoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeeHasRoleMapper.fromId(null)).isNull();
    }
}
