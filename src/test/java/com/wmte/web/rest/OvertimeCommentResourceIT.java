package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.OvertimeComment;
import com.wmte.repository.OvertimeCommentRepository;
import com.wmte.service.OvertimeCommentService;
import com.wmte.service.dto.OvertimeCommentDTO;
import com.wmte.service.mapper.OvertimeCommentMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.wmte.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OvertimeCommentResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class OvertimeCommentResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_DATE = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_EMAIL = "BBBBBBBBBB";

    @Autowired
    private OvertimeCommentRepository overtimeCommentRepository;

    @Autowired
    private OvertimeCommentMapper overtimeCommentMapper;

    @Autowired
    private OvertimeCommentService overtimeCommentService;

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

    private MockMvc restOvertimeCommentMockMvc;

    private OvertimeComment overtimeComment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OvertimeCommentResource overtimeCommentResource = new OvertimeCommentResource(overtimeCommentService);
        this.restOvertimeCommentMockMvc = MockMvcBuilders.standaloneSetup(overtimeCommentResource)
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
    public static OvertimeComment createEntity(EntityManager em) {
        OvertimeComment overtimeComment = new OvertimeComment()
            .comment(DEFAULT_COMMENT)
            .createdDate(DEFAULT_CREATED_DATE)
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .employeeEmail(DEFAULT_EMPLOYEE_EMAIL);
        return overtimeComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OvertimeComment createUpdatedEntity(EntityManager em) {
        OvertimeComment overtimeComment = new OvertimeComment()
            .comment(UPDATED_COMMENT)
            .createdDate(UPDATED_CREATED_DATE)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL);
        return overtimeComment;
    }

    @BeforeEach
    public void initTest() {
        overtimeComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createOvertimeComment() throws Exception {
        int databaseSizeBeforeCreate = overtimeCommentRepository.findAll().size();

        // Create the OvertimeComment
        OvertimeCommentDTO overtimeCommentDTO = overtimeCommentMapper.toDto(overtimeComment);
        restOvertimeCommentMockMvc.perform(post("/api/overtime-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the OvertimeComment in the database
        List<OvertimeComment> overtimeCommentList = overtimeCommentRepository.findAll();
        assertThat(overtimeCommentList).hasSize(databaseSizeBeforeCreate + 1);
        OvertimeComment testOvertimeComment = overtimeCommentList.get(overtimeCommentList.size() - 1);
        assertThat(testOvertimeComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testOvertimeComment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOvertimeComment.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testOvertimeComment.getEmployeeEmail()).isEqualTo(DEFAULT_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    public void createOvertimeCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = overtimeCommentRepository.findAll().size();

        // Create the OvertimeComment with an existing ID
        overtimeComment.setId(1L);
        OvertimeCommentDTO overtimeCommentDTO = overtimeCommentMapper.toDto(overtimeComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOvertimeCommentMockMvc.perform(post("/api/overtime-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OvertimeComment in the database
        List<OvertimeComment> overtimeCommentList = overtimeCommentRepository.findAll();
        assertThat(overtimeCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOvertimeComments() throws Exception {
        // Initialize the database
        overtimeCommentRepository.saveAndFlush(overtimeComment);

        // Get all the overtimeCommentList
        restOvertimeCommentMockMvc.perform(get("/api/overtime-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overtimeComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].employeeEmail").value(hasItem(DEFAULT_EMPLOYEE_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getOvertimeComment() throws Exception {
        // Initialize the database
        overtimeCommentRepository.saveAndFlush(overtimeComment);

        // Get the overtimeComment
        restOvertimeCommentMockMvc.perform(get("/api/overtime-comments/{id}", overtimeComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(overtimeComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
            .andExpect(jsonPath("$.employeeEmail").value(DEFAULT_EMPLOYEE_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOvertimeComment() throws Exception {
        // Get the overtimeComment
        restOvertimeCommentMockMvc.perform(get("/api/overtime-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOvertimeComment() throws Exception {
        // Initialize the database
        overtimeCommentRepository.saveAndFlush(overtimeComment);

        int databaseSizeBeforeUpdate = overtimeCommentRepository.findAll().size();

        // Update the overtimeComment
        OvertimeComment updatedOvertimeComment = overtimeCommentRepository.findById(overtimeComment.getId()).get();
        // Disconnect from session so that the updates on updatedOvertimeComment are not directly saved in db
        em.detach(updatedOvertimeComment);
        updatedOvertimeComment
            .comment(UPDATED_COMMENT)
            .createdDate(UPDATED_CREATED_DATE)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL);
        OvertimeCommentDTO overtimeCommentDTO = overtimeCommentMapper.toDto(updatedOvertimeComment);

        restOvertimeCommentMockMvc.perform(put("/api/overtime-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeCommentDTO)))
            .andExpect(status().isOk());

        // Validate the OvertimeComment in the database
        List<OvertimeComment> overtimeCommentList = overtimeCommentRepository.findAll();
        assertThat(overtimeCommentList).hasSize(databaseSizeBeforeUpdate);
        OvertimeComment testOvertimeComment = overtimeCommentList.get(overtimeCommentList.size() - 1);
        assertThat(testOvertimeComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testOvertimeComment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOvertimeComment.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testOvertimeComment.getEmployeeEmail()).isEqualTo(UPDATED_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingOvertimeComment() throws Exception {
        int databaseSizeBeforeUpdate = overtimeCommentRepository.findAll().size();

        // Create the OvertimeComment
        OvertimeCommentDTO overtimeCommentDTO = overtimeCommentMapper.toDto(overtimeComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOvertimeCommentMockMvc.perform(put("/api/overtime-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OvertimeComment in the database
        List<OvertimeComment> overtimeCommentList = overtimeCommentRepository.findAll();
        assertThat(overtimeCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOvertimeComment() throws Exception {
        // Initialize the database
        overtimeCommentRepository.saveAndFlush(overtimeComment);

        int databaseSizeBeforeDelete = overtimeCommentRepository.findAll().size();

        // Delete the overtimeComment
        restOvertimeCommentMockMvc.perform(delete("/api/overtime-comments/{id}", overtimeComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OvertimeComment> overtimeCommentList = overtimeCommentRepository.findAll();
        assertThat(overtimeCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OvertimeComment.class);
        OvertimeComment overtimeComment1 = new OvertimeComment();
        overtimeComment1.setId(1L);
        OvertimeComment overtimeComment2 = new OvertimeComment();
        overtimeComment2.setId(overtimeComment1.getId());
        assertThat(overtimeComment1).isEqualTo(overtimeComment2);
        overtimeComment2.setId(2L);
        assertThat(overtimeComment1).isNotEqualTo(overtimeComment2);
        overtimeComment1.setId(null);
        assertThat(overtimeComment1).isNotEqualTo(overtimeComment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OvertimeCommentDTO.class);
        OvertimeCommentDTO overtimeCommentDTO1 = new OvertimeCommentDTO();
        overtimeCommentDTO1.setId(1L);
        OvertimeCommentDTO overtimeCommentDTO2 = new OvertimeCommentDTO();
        assertThat(overtimeCommentDTO1).isNotEqualTo(overtimeCommentDTO2);
        overtimeCommentDTO2.setId(overtimeCommentDTO1.getId());
        assertThat(overtimeCommentDTO1).isEqualTo(overtimeCommentDTO2);
        overtimeCommentDTO2.setId(2L);
        assertThat(overtimeCommentDTO1).isNotEqualTo(overtimeCommentDTO2);
        overtimeCommentDTO1.setId(null);
        assertThat(overtimeCommentDTO1).isNotEqualTo(overtimeCommentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(overtimeCommentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(overtimeCommentMapper.fromId(null)).isNull();
    }
}
