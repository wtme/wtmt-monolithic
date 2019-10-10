package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.DepartmentApprove;
import com.wmte.repository.DepartmentApproveRepository;
import com.wmte.service.DepartmentApproveService;
import com.wmte.service.dto.DepartmentApproveDTO;
import com.wmte.service.mapper.DepartmentApproveMapper;
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
 * Integration tests for the {@link DepartmentApproveResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class DepartmentApproveResourceIT {

    @Autowired
    private DepartmentApproveRepository departmentApproveRepository;

    @Autowired
    private DepartmentApproveMapper departmentApproveMapper;

    @Autowired
    private DepartmentApproveService departmentApproveService;

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

    private MockMvc restDepartmentApproveMockMvc;

    private DepartmentApprove departmentApprove;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartmentApproveResource departmentApproveResource = new DepartmentApproveResource(departmentApproveService);
        this.restDepartmentApproveMockMvc = MockMvcBuilders.standaloneSetup(departmentApproveResource)
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
    public static DepartmentApprove createEntity(EntityManager em) {
        DepartmentApprove departmentApprove = new DepartmentApprove();
        return departmentApprove;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartmentApprove createUpdatedEntity(EntityManager em) {
        DepartmentApprove departmentApprove = new DepartmentApprove();
        return departmentApprove;
    }

    @BeforeEach
    public void initTest() {
        departmentApprove = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartmentApprove() throws Exception {
        int databaseSizeBeforeCreate = departmentApproveRepository.findAll().size();

        // Create the DepartmentApprove
        DepartmentApproveDTO departmentApproveDTO = departmentApproveMapper.toDto(departmentApprove);
        restDepartmentApproveMockMvc.perform(post("/api/department-approves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentApproveDTO)))
            .andExpect(status().isCreated());

        // Validate the DepartmentApprove in the database
        List<DepartmentApprove> departmentApproveList = departmentApproveRepository.findAll();
        assertThat(departmentApproveList).hasSize(databaseSizeBeforeCreate + 1);
        DepartmentApprove testDepartmentApprove = departmentApproveList.get(departmentApproveList.size() - 1);
    }

    @Test
    @Transactional
    public void createDepartmentApproveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentApproveRepository.findAll().size();

        // Create the DepartmentApprove with an existing ID
        departmentApprove.setId(1L);
        DepartmentApproveDTO departmentApproveDTO = departmentApproveMapper.toDto(departmentApprove);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentApproveMockMvc.perform(post("/api/department-approves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentApproveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentApprove in the database
        List<DepartmentApprove> departmentApproveList = departmentApproveRepository.findAll();
        assertThat(departmentApproveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDepartmentApproves() throws Exception {
        // Initialize the database
        departmentApproveRepository.saveAndFlush(departmentApprove);

        // Get all the departmentApproveList
        restDepartmentApproveMockMvc.perform(get("/api/department-approves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentApprove.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDepartmentApprove() throws Exception {
        // Initialize the database
        departmentApproveRepository.saveAndFlush(departmentApprove);

        // Get the departmentApprove
        restDepartmentApproveMockMvc.perform(get("/api/department-approves/{id}", departmentApprove.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departmentApprove.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartmentApprove() throws Exception {
        // Get the departmentApprove
        restDepartmentApproveMockMvc.perform(get("/api/department-approves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartmentApprove() throws Exception {
        // Initialize the database
        departmentApproveRepository.saveAndFlush(departmentApprove);

        int databaseSizeBeforeUpdate = departmentApproveRepository.findAll().size();

        // Update the departmentApprove
        DepartmentApprove updatedDepartmentApprove = departmentApproveRepository.findById(departmentApprove.getId()).get();
        // Disconnect from session so that the updates on updatedDepartmentApprove are not directly saved in db
        em.detach(updatedDepartmentApprove);
        DepartmentApproveDTO departmentApproveDTO = departmentApproveMapper.toDto(updatedDepartmentApprove);

        restDepartmentApproveMockMvc.perform(put("/api/department-approves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentApproveDTO)))
            .andExpect(status().isOk());

        // Validate the DepartmentApprove in the database
        List<DepartmentApprove> departmentApproveList = departmentApproveRepository.findAll();
        assertThat(departmentApproveList).hasSize(databaseSizeBeforeUpdate);
        DepartmentApprove testDepartmentApprove = departmentApproveList.get(departmentApproveList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartmentApprove() throws Exception {
        int databaseSizeBeforeUpdate = departmentApproveRepository.findAll().size();

        // Create the DepartmentApprove
        DepartmentApproveDTO departmentApproveDTO = departmentApproveMapper.toDto(departmentApprove);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentApproveMockMvc.perform(put("/api/department-approves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentApproveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentApprove in the database
        List<DepartmentApprove> departmentApproveList = departmentApproveRepository.findAll();
        assertThat(departmentApproveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartmentApprove() throws Exception {
        // Initialize the database
        departmentApproveRepository.saveAndFlush(departmentApprove);

        int databaseSizeBeforeDelete = departmentApproveRepository.findAll().size();

        // Delete the departmentApprove
        restDepartmentApproveMockMvc.perform(delete("/api/department-approves/{id}", departmentApprove.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepartmentApprove> departmentApproveList = departmentApproveRepository.findAll();
        assertThat(departmentApproveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentApprove.class);
        DepartmentApprove departmentApprove1 = new DepartmentApprove();
        departmentApprove1.setId(1L);
        DepartmentApprove departmentApprove2 = new DepartmentApprove();
        departmentApprove2.setId(departmentApprove1.getId());
        assertThat(departmentApprove1).isEqualTo(departmentApprove2);
        departmentApprove2.setId(2L);
        assertThat(departmentApprove1).isNotEqualTo(departmentApprove2);
        departmentApprove1.setId(null);
        assertThat(departmentApprove1).isNotEqualTo(departmentApprove2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentApproveDTO.class);
        DepartmentApproveDTO departmentApproveDTO1 = new DepartmentApproveDTO();
        departmentApproveDTO1.setId(1L);
        DepartmentApproveDTO departmentApproveDTO2 = new DepartmentApproveDTO();
        assertThat(departmentApproveDTO1).isNotEqualTo(departmentApproveDTO2);
        departmentApproveDTO2.setId(departmentApproveDTO1.getId());
        assertThat(departmentApproveDTO1).isEqualTo(departmentApproveDTO2);
        departmentApproveDTO2.setId(2L);
        assertThat(departmentApproveDTO1).isNotEqualTo(departmentApproveDTO2);
        departmentApproveDTO1.setId(null);
        assertThat(departmentApproveDTO1).isNotEqualTo(departmentApproveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(departmentApproveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(departmentApproveMapper.fromId(null)).isNull();
    }
}
