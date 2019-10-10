package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.DepartmentRole;
import com.wmte.repository.DepartmentRoleRepository;
import com.wmte.service.DepartmentRoleService;
import com.wmte.service.dto.DepartmentRoleDTO;
import com.wmte.service.mapper.DepartmentRoleMapper;
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
 * Integration tests for the {@link DepartmentRoleResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class DepartmentRoleResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DepartmentRoleRepository departmentRoleRepository;

    @Autowired
    private DepartmentRoleMapper departmentRoleMapper;

    @Autowired
    private DepartmentRoleService departmentRoleService;

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

    private MockMvc restDepartmentRoleMockMvc;

    private DepartmentRole departmentRole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartmentRoleResource departmentRoleResource = new DepartmentRoleResource(departmentRoleService);
        this.restDepartmentRoleMockMvc = MockMvcBuilders.standaloneSetup(departmentRoleResource)
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
    public static DepartmentRole createEntity(EntityManager em) {
        DepartmentRole departmentRole = new DepartmentRole()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return departmentRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartmentRole createUpdatedEntity(EntityManager em) {
        DepartmentRole departmentRole = new DepartmentRole()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return departmentRole;
    }

    @BeforeEach
    public void initTest() {
        departmentRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartmentRole() throws Exception {
        int databaseSizeBeforeCreate = departmentRoleRepository.findAll().size();

        // Create the DepartmentRole
        DepartmentRoleDTO departmentRoleDTO = departmentRoleMapper.toDto(departmentRole);
        restDepartmentRoleMockMvc.perform(post("/api/department-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the DepartmentRole in the database
        List<DepartmentRole> departmentRoleList = departmentRoleRepository.findAll();
        assertThat(departmentRoleList).hasSize(databaseSizeBeforeCreate + 1);
        DepartmentRole testDepartmentRole = departmentRoleList.get(departmentRoleList.size() - 1);
        assertThat(testDepartmentRole.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDepartmentRole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDepartmentRole.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDepartmentRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentRoleRepository.findAll().size();

        // Create the DepartmentRole with an existing ID
        departmentRole.setId(1L);
        DepartmentRoleDTO departmentRoleDTO = departmentRoleMapper.toDto(departmentRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentRoleMockMvc.perform(post("/api/department-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentRole in the database
        List<DepartmentRole> departmentRoleList = departmentRoleRepository.findAll();
        assertThat(departmentRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = departmentRoleRepository.findAll().size();
        // set the field null
        departmentRole.setCode(null);

        // Create the DepartmentRole, which fails.
        DepartmentRoleDTO departmentRoleDTO = departmentRoleMapper.toDto(departmentRole);

        restDepartmentRoleMockMvc.perform(post("/api/department-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRoleDTO)))
            .andExpect(status().isBadRequest());

        List<DepartmentRole> departmentRoleList = departmentRoleRepository.findAll();
        assertThat(departmentRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = departmentRoleRepository.findAll().size();
        // set the field null
        departmentRole.setName(null);

        // Create the DepartmentRole, which fails.
        DepartmentRoleDTO departmentRoleDTO = departmentRoleMapper.toDto(departmentRole);

        restDepartmentRoleMockMvc.perform(post("/api/department-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRoleDTO)))
            .andExpect(status().isBadRequest());

        List<DepartmentRole> departmentRoleList = departmentRoleRepository.findAll();
        assertThat(departmentRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartmentRoles() throws Exception {
        // Initialize the database
        departmentRoleRepository.saveAndFlush(departmentRole);

        // Get all the departmentRoleList
        restDepartmentRoleMockMvc.perform(get("/api/department-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getDepartmentRole() throws Exception {
        // Initialize the database
        departmentRoleRepository.saveAndFlush(departmentRole);

        // Get the departmentRole
        restDepartmentRoleMockMvc.perform(get("/api/department-roles/{id}", departmentRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departmentRole.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartmentRole() throws Exception {
        // Get the departmentRole
        restDepartmentRoleMockMvc.perform(get("/api/department-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartmentRole() throws Exception {
        // Initialize the database
        departmentRoleRepository.saveAndFlush(departmentRole);

        int databaseSizeBeforeUpdate = departmentRoleRepository.findAll().size();

        // Update the departmentRole
        DepartmentRole updatedDepartmentRole = departmentRoleRepository.findById(departmentRole.getId()).get();
        // Disconnect from session so that the updates on updatedDepartmentRole are not directly saved in db
        em.detach(updatedDepartmentRole);
        updatedDepartmentRole
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        DepartmentRoleDTO departmentRoleDTO = departmentRoleMapper.toDto(updatedDepartmentRole);

        restDepartmentRoleMockMvc.perform(put("/api/department-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRoleDTO)))
            .andExpect(status().isOk());

        // Validate the DepartmentRole in the database
        List<DepartmentRole> departmentRoleList = departmentRoleRepository.findAll();
        assertThat(departmentRoleList).hasSize(databaseSizeBeforeUpdate);
        DepartmentRole testDepartmentRole = departmentRoleList.get(departmentRoleList.size() - 1);
        assertThat(testDepartmentRole.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDepartmentRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartmentRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartmentRole() throws Exception {
        int databaseSizeBeforeUpdate = departmentRoleRepository.findAll().size();

        // Create the DepartmentRole
        DepartmentRoleDTO departmentRoleDTO = departmentRoleMapper.toDto(departmentRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentRoleMockMvc.perform(put("/api/department-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentRole in the database
        List<DepartmentRole> departmentRoleList = departmentRoleRepository.findAll();
        assertThat(departmentRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartmentRole() throws Exception {
        // Initialize the database
        departmentRoleRepository.saveAndFlush(departmentRole);

        int databaseSizeBeforeDelete = departmentRoleRepository.findAll().size();

        // Delete the departmentRole
        restDepartmentRoleMockMvc.perform(delete("/api/department-roles/{id}", departmentRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepartmentRole> departmentRoleList = departmentRoleRepository.findAll();
        assertThat(departmentRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentRole.class);
        DepartmentRole departmentRole1 = new DepartmentRole();
        departmentRole1.setId(1L);
        DepartmentRole departmentRole2 = new DepartmentRole();
        departmentRole2.setId(departmentRole1.getId());
        assertThat(departmentRole1).isEqualTo(departmentRole2);
        departmentRole2.setId(2L);
        assertThat(departmentRole1).isNotEqualTo(departmentRole2);
        departmentRole1.setId(null);
        assertThat(departmentRole1).isNotEqualTo(departmentRole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentRoleDTO.class);
        DepartmentRoleDTO departmentRoleDTO1 = new DepartmentRoleDTO();
        departmentRoleDTO1.setId(1L);
        DepartmentRoleDTO departmentRoleDTO2 = new DepartmentRoleDTO();
        assertThat(departmentRoleDTO1).isNotEqualTo(departmentRoleDTO2);
        departmentRoleDTO2.setId(departmentRoleDTO1.getId());
        assertThat(departmentRoleDTO1).isEqualTo(departmentRoleDTO2);
        departmentRoleDTO2.setId(2L);
        assertThat(departmentRoleDTO1).isNotEqualTo(departmentRoleDTO2);
        departmentRoleDTO1.setId(null);
        assertThat(departmentRoleDTO1).isNotEqualTo(departmentRoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(departmentRoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(departmentRoleMapper.fromId(null)).isNull();
    }
}
