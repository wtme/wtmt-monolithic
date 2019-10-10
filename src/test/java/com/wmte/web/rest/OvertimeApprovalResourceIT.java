package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.OvertimeApproval;
import com.wmte.repository.OvertimeApprovalRepository;
import com.wmte.service.OvertimeApprovalService;
import com.wmte.service.dto.OvertimeApprovalDTO;
import com.wmte.service.mapper.OvertimeApprovalMapper;
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
 * Integration tests for the {@link OvertimeApprovalResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class OvertimeApprovalResourceIT {

    private static final String DEFAULT_APPROVER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_EMAIL = "BBBBBBBBBB";

    @Autowired
    private OvertimeApprovalRepository overtimeApprovalRepository;

    @Autowired
    private OvertimeApprovalMapper overtimeApprovalMapper;

    @Autowired
    private OvertimeApprovalService overtimeApprovalService;

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

    private MockMvc restOvertimeApprovalMockMvc;

    private OvertimeApproval overtimeApproval;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OvertimeApprovalResource overtimeApprovalResource = new OvertimeApprovalResource(overtimeApprovalService);
        this.restOvertimeApprovalMockMvc = MockMvcBuilders.standaloneSetup(overtimeApprovalResource)
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
    public static OvertimeApproval createEntity(EntityManager em) {
        OvertimeApproval overtimeApproval = new OvertimeApproval()
            .approverName(DEFAULT_APPROVER_NAME)
            .approverEmail(DEFAULT_APPROVER_EMAIL);
        return overtimeApproval;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OvertimeApproval createUpdatedEntity(EntityManager em) {
        OvertimeApproval overtimeApproval = new OvertimeApproval()
            .approverName(UPDATED_APPROVER_NAME)
            .approverEmail(UPDATED_APPROVER_EMAIL);
        return overtimeApproval;
    }

    @BeforeEach
    public void initTest() {
        overtimeApproval = createEntity(em);
    }

    @Test
    @Transactional
    public void createOvertimeApproval() throws Exception {
        int databaseSizeBeforeCreate = overtimeApprovalRepository.findAll().size();

        // Create the OvertimeApproval
        OvertimeApprovalDTO overtimeApprovalDTO = overtimeApprovalMapper.toDto(overtimeApproval);
        restOvertimeApprovalMockMvc.perform(post("/api/overtime-approvals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeApprovalDTO)))
            .andExpect(status().isCreated());

        // Validate the OvertimeApproval in the database
        List<OvertimeApproval> overtimeApprovalList = overtimeApprovalRepository.findAll();
        assertThat(overtimeApprovalList).hasSize(databaseSizeBeforeCreate + 1);
        OvertimeApproval testOvertimeApproval = overtimeApprovalList.get(overtimeApprovalList.size() - 1);
        assertThat(testOvertimeApproval.getApproverName()).isEqualTo(DEFAULT_APPROVER_NAME);
        assertThat(testOvertimeApproval.getApproverEmail()).isEqualTo(DEFAULT_APPROVER_EMAIL);
    }

    @Test
    @Transactional
    public void createOvertimeApprovalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = overtimeApprovalRepository.findAll().size();

        // Create the OvertimeApproval with an existing ID
        overtimeApproval.setId(1L);
        OvertimeApprovalDTO overtimeApprovalDTO = overtimeApprovalMapper.toDto(overtimeApproval);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOvertimeApprovalMockMvc.perform(post("/api/overtime-approvals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeApprovalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OvertimeApproval in the database
        List<OvertimeApproval> overtimeApprovalList = overtimeApprovalRepository.findAll();
        assertThat(overtimeApprovalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOvertimeApprovals() throws Exception {
        // Initialize the database
        overtimeApprovalRepository.saveAndFlush(overtimeApproval);

        // Get all the overtimeApprovalList
        restOvertimeApprovalMockMvc.perform(get("/api/overtime-approvals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overtimeApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].approverName").value(hasItem(DEFAULT_APPROVER_NAME.toString())))
            .andExpect(jsonPath("$.[*].approverEmail").value(hasItem(DEFAULT_APPROVER_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getOvertimeApproval() throws Exception {
        // Initialize the database
        overtimeApprovalRepository.saveAndFlush(overtimeApproval);

        // Get the overtimeApproval
        restOvertimeApprovalMockMvc.perform(get("/api/overtime-approvals/{id}", overtimeApproval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(overtimeApproval.getId().intValue()))
            .andExpect(jsonPath("$.approverName").value(DEFAULT_APPROVER_NAME.toString()))
            .andExpect(jsonPath("$.approverEmail").value(DEFAULT_APPROVER_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOvertimeApproval() throws Exception {
        // Get the overtimeApproval
        restOvertimeApprovalMockMvc.perform(get("/api/overtime-approvals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOvertimeApproval() throws Exception {
        // Initialize the database
        overtimeApprovalRepository.saveAndFlush(overtimeApproval);

        int databaseSizeBeforeUpdate = overtimeApprovalRepository.findAll().size();

        // Update the overtimeApproval
        OvertimeApproval updatedOvertimeApproval = overtimeApprovalRepository.findById(overtimeApproval.getId()).get();
        // Disconnect from session so that the updates on updatedOvertimeApproval are not directly saved in db
        em.detach(updatedOvertimeApproval);
        updatedOvertimeApproval
            .approverName(UPDATED_APPROVER_NAME)
            .approverEmail(UPDATED_APPROVER_EMAIL);
        OvertimeApprovalDTO overtimeApprovalDTO = overtimeApprovalMapper.toDto(updatedOvertimeApproval);

        restOvertimeApprovalMockMvc.perform(put("/api/overtime-approvals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeApprovalDTO)))
            .andExpect(status().isOk());

        // Validate the OvertimeApproval in the database
        List<OvertimeApproval> overtimeApprovalList = overtimeApprovalRepository.findAll();
        assertThat(overtimeApprovalList).hasSize(databaseSizeBeforeUpdate);
        OvertimeApproval testOvertimeApproval = overtimeApprovalList.get(overtimeApprovalList.size() - 1);
        assertThat(testOvertimeApproval.getApproverName()).isEqualTo(UPDATED_APPROVER_NAME);
        assertThat(testOvertimeApproval.getApproverEmail()).isEqualTo(UPDATED_APPROVER_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingOvertimeApproval() throws Exception {
        int databaseSizeBeforeUpdate = overtimeApprovalRepository.findAll().size();

        // Create the OvertimeApproval
        OvertimeApprovalDTO overtimeApprovalDTO = overtimeApprovalMapper.toDto(overtimeApproval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOvertimeApprovalMockMvc.perform(put("/api/overtime-approvals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeApprovalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OvertimeApproval in the database
        List<OvertimeApproval> overtimeApprovalList = overtimeApprovalRepository.findAll();
        assertThat(overtimeApprovalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOvertimeApproval() throws Exception {
        // Initialize the database
        overtimeApprovalRepository.saveAndFlush(overtimeApproval);

        int databaseSizeBeforeDelete = overtimeApprovalRepository.findAll().size();

        // Delete the overtimeApproval
        restOvertimeApprovalMockMvc.perform(delete("/api/overtime-approvals/{id}", overtimeApproval.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OvertimeApproval> overtimeApprovalList = overtimeApprovalRepository.findAll();
        assertThat(overtimeApprovalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OvertimeApproval.class);
        OvertimeApproval overtimeApproval1 = new OvertimeApproval();
        overtimeApproval1.setId(1L);
        OvertimeApproval overtimeApproval2 = new OvertimeApproval();
        overtimeApproval2.setId(overtimeApproval1.getId());
        assertThat(overtimeApproval1).isEqualTo(overtimeApproval2);
        overtimeApproval2.setId(2L);
        assertThat(overtimeApproval1).isNotEqualTo(overtimeApproval2);
        overtimeApproval1.setId(null);
        assertThat(overtimeApproval1).isNotEqualTo(overtimeApproval2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OvertimeApprovalDTO.class);
        OvertimeApprovalDTO overtimeApprovalDTO1 = new OvertimeApprovalDTO();
        overtimeApprovalDTO1.setId(1L);
        OvertimeApprovalDTO overtimeApprovalDTO2 = new OvertimeApprovalDTO();
        assertThat(overtimeApprovalDTO1).isNotEqualTo(overtimeApprovalDTO2);
        overtimeApprovalDTO2.setId(overtimeApprovalDTO1.getId());
        assertThat(overtimeApprovalDTO1).isEqualTo(overtimeApprovalDTO2);
        overtimeApprovalDTO2.setId(2L);
        assertThat(overtimeApprovalDTO1).isNotEqualTo(overtimeApprovalDTO2);
        overtimeApprovalDTO1.setId(null);
        assertThat(overtimeApprovalDTO1).isNotEqualTo(overtimeApprovalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(overtimeApprovalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(overtimeApprovalMapper.fromId(null)).isNull();
    }
}
