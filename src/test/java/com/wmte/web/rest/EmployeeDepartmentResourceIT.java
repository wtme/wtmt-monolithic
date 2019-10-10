package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.EmployeeDepartment;
import com.wmte.repository.EmployeeDepartmentRepository;
import com.wmte.service.EmployeeDepartmentService;
import com.wmte.service.dto.EmployeeDepartmentDTO;
import com.wmte.service.mapper.EmployeeDepartmentMapper;
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
 * Integration tests for the {@link EmployeeDepartmentResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class EmployeeDepartmentResourceIT {

    @Autowired
    private EmployeeDepartmentRepository employeeDepartmentRepository;

    @Autowired
    private EmployeeDepartmentMapper employeeDepartmentMapper;

    @Autowired
    private EmployeeDepartmentService employeeDepartmentService;

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

    private MockMvc restEmployeeDepartmentMockMvc;

    private EmployeeDepartment employeeDepartment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeDepartmentResource employeeDepartmentResource = new EmployeeDepartmentResource(employeeDepartmentService);
        this.restEmployeeDepartmentMockMvc = MockMvcBuilders.standaloneSetup(employeeDepartmentResource)
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
    public static EmployeeDepartment createEntity(EntityManager em) {
        EmployeeDepartment employeeDepartment = new EmployeeDepartment();
        return employeeDepartment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDepartment createUpdatedEntity(EntityManager em) {
        EmployeeDepartment employeeDepartment = new EmployeeDepartment();
        return employeeDepartment;
    }

    @BeforeEach
    public void initTest() {
        employeeDepartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeDepartment() throws Exception {
        int databaseSizeBeforeCreate = employeeDepartmentRepository.findAll().size();

        // Create the EmployeeDepartment
        EmployeeDepartmentDTO employeeDepartmentDTO = employeeDepartmentMapper.toDto(employeeDepartment);
        restEmployeeDepartmentMockMvc.perform(post("/api/employee-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDepartmentDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeDepartment in the database
        List<EmployeeDepartment> employeeDepartmentList = employeeDepartmentRepository.findAll();
        assertThat(employeeDepartmentList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDepartment testEmployeeDepartment = employeeDepartmentList.get(employeeDepartmentList.size() - 1);
    }

    @Test
    @Transactional
    public void createEmployeeDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeDepartmentRepository.findAll().size();

        // Create the EmployeeDepartment with an existing ID
        employeeDepartment.setId(1L);
        EmployeeDepartmentDTO employeeDepartmentDTO = employeeDepartmentMapper.toDto(employeeDepartment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDepartmentMockMvc.perform(post("/api/employee-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDepartmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDepartment in the database
        List<EmployeeDepartment> employeeDepartmentList = employeeDepartmentRepository.findAll();
        assertThat(employeeDepartmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeDepartments() throws Exception {
        // Initialize the database
        employeeDepartmentRepository.saveAndFlush(employeeDepartment);

        // Get all the employeeDepartmentList
        restEmployeeDepartmentMockMvc.perform(get("/api/employee-departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDepartment.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeDepartment() throws Exception {
        // Initialize the database
        employeeDepartmentRepository.saveAndFlush(employeeDepartment);

        // Get the employeeDepartment
        restEmployeeDepartmentMockMvc.perform(get("/api/employee-departments/{id}", employeeDepartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDepartment.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeDepartment() throws Exception {
        // Get the employeeDepartment
        restEmployeeDepartmentMockMvc.perform(get("/api/employee-departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeDepartment() throws Exception {
        // Initialize the database
        employeeDepartmentRepository.saveAndFlush(employeeDepartment);

        int databaseSizeBeforeUpdate = employeeDepartmentRepository.findAll().size();

        // Update the employeeDepartment
        EmployeeDepartment updatedEmployeeDepartment = employeeDepartmentRepository.findById(employeeDepartment.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDepartment are not directly saved in db
        em.detach(updatedEmployeeDepartment);
        EmployeeDepartmentDTO employeeDepartmentDTO = employeeDepartmentMapper.toDto(updatedEmployeeDepartment);

        restEmployeeDepartmentMockMvc.perform(put("/api/employee-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDepartmentDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeDepartment in the database
        List<EmployeeDepartment> employeeDepartmentList = employeeDepartmentRepository.findAll();
        assertThat(employeeDepartmentList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDepartment testEmployeeDepartment = employeeDepartmentList.get(employeeDepartmentList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeDepartment() throws Exception {
        int databaseSizeBeforeUpdate = employeeDepartmentRepository.findAll().size();

        // Create the EmployeeDepartment
        EmployeeDepartmentDTO employeeDepartmentDTO = employeeDepartmentMapper.toDto(employeeDepartment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDepartmentMockMvc.perform(put("/api/employee-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDepartmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDepartment in the database
        List<EmployeeDepartment> employeeDepartmentList = employeeDepartmentRepository.findAll();
        assertThat(employeeDepartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeDepartment() throws Exception {
        // Initialize the database
        employeeDepartmentRepository.saveAndFlush(employeeDepartment);

        int databaseSizeBeforeDelete = employeeDepartmentRepository.findAll().size();

        // Delete the employeeDepartment
        restEmployeeDepartmentMockMvc.perform(delete("/api/employee-departments/{id}", employeeDepartment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDepartment> employeeDepartmentList = employeeDepartmentRepository.findAll();
        assertThat(employeeDepartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDepartment.class);
        EmployeeDepartment employeeDepartment1 = new EmployeeDepartment();
        employeeDepartment1.setId(1L);
        EmployeeDepartment employeeDepartment2 = new EmployeeDepartment();
        employeeDepartment2.setId(employeeDepartment1.getId());
        assertThat(employeeDepartment1).isEqualTo(employeeDepartment2);
        employeeDepartment2.setId(2L);
        assertThat(employeeDepartment1).isNotEqualTo(employeeDepartment2);
        employeeDepartment1.setId(null);
        assertThat(employeeDepartment1).isNotEqualTo(employeeDepartment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDepartmentDTO.class);
        EmployeeDepartmentDTO employeeDepartmentDTO1 = new EmployeeDepartmentDTO();
        employeeDepartmentDTO1.setId(1L);
        EmployeeDepartmentDTO employeeDepartmentDTO2 = new EmployeeDepartmentDTO();
        assertThat(employeeDepartmentDTO1).isNotEqualTo(employeeDepartmentDTO2);
        employeeDepartmentDTO2.setId(employeeDepartmentDTO1.getId());
        assertThat(employeeDepartmentDTO1).isEqualTo(employeeDepartmentDTO2);
        employeeDepartmentDTO2.setId(2L);
        assertThat(employeeDepartmentDTO1).isNotEqualTo(employeeDepartmentDTO2);
        employeeDepartmentDTO1.setId(null);
        assertThat(employeeDepartmentDTO1).isNotEqualTo(employeeDepartmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeDepartmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeeDepartmentMapper.fromId(null)).isNull();
    }
}
