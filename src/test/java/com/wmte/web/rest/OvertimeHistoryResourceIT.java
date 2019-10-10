package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.OvertimeHistory;
import com.wmte.repository.OvertimeHistoryRepository;
import com.wmte.service.OvertimeHistoryService;
import com.wmte.service.dto.OvertimeHistoryDTO;
import com.wmte.service.mapper.OvertimeHistoryMapper;
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

import com.wmte.domain.enumeration.OvertimeStatus;
/**
 * Integration tests for the {@link OvertimeHistoryResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class OvertimeHistoryResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_DATE = Instant.ofEpochMilli(-1L);

    private static final OvertimeStatus DEFAULT_STATUS = OvertimeStatus.OPEN;
    private static final OvertimeStatus UPDATED_STATUS = OvertimeStatus.APPROVED;

    private static final String DEFAULT_CHANGE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_WHO = "AAAAAAAAAA";
    private static final String UPDATED_WHO = "BBBBBBBBBB";

    @Autowired
    private OvertimeHistoryRepository overtimeHistoryRepository;

    @Autowired
    private OvertimeHistoryMapper overtimeHistoryMapper;

    @Autowired
    private OvertimeHistoryService overtimeHistoryService;

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

    private MockMvc restOvertimeHistoryMockMvc;

    private OvertimeHistory overtimeHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OvertimeHistoryResource overtimeHistoryResource = new OvertimeHistoryResource(overtimeHistoryService);
        this.restOvertimeHistoryMockMvc = MockMvcBuilders.standaloneSetup(overtimeHistoryResource)
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
    public static OvertimeHistory createEntity(EntityManager em) {
        OvertimeHistory overtimeHistory = new OvertimeHistory()
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS)
            .changeType(DEFAULT_CHANGE_TYPE)
            .who(DEFAULT_WHO);
        return overtimeHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OvertimeHistory createUpdatedEntity(EntityManager em) {
        OvertimeHistory overtimeHistory = new OvertimeHistory()
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .changeType(UPDATED_CHANGE_TYPE)
            .who(UPDATED_WHO);
        return overtimeHistory;
    }

    @BeforeEach
    public void initTest() {
        overtimeHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createOvertimeHistory() throws Exception {
        int databaseSizeBeforeCreate = overtimeHistoryRepository.findAll().size();

        // Create the OvertimeHistory
        OvertimeHistoryDTO overtimeHistoryDTO = overtimeHistoryMapper.toDto(overtimeHistory);
        restOvertimeHistoryMockMvc.perform(post("/api/overtime-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the OvertimeHistory in the database
        List<OvertimeHistory> overtimeHistoryList = overtimeHistoryRepository.findAll();
        assertThat(overtimeHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        OvertimeHistory testOvertimeHistory = overtimeHistoryList.get(overtimeHistoryList.size() - 1);
        assertThat(testOvertimeHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOvertimeHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOvertimeHistory.getChangeType()).isEqualTo(DEFAULT_CHANGE_TYPE);
        assertThat(testOvertimeHistory.getWho()).isEqualTo(DEFAULT_WHO);
    }

    @Test
    @Transactional
    public void createOvertimeHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = overtimeHistoryRepository.findAll().size();

        // Create the OvertimeHistory with an existing ID
        overtimeHistory.setId(1L);
        OvertimeHistoryDTO overtimeHistoryDTO = overtimeHistoryMapper.toDto(overtimeHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOvertimeHistoryMockMvc.perform(post("/api/overtime-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OvertimeHistory in the database
        List<OvertimeHistory> overtimeHistoryList = overtimeHistoryRepository.findAll();
        assertThat(overtimeHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOvertimeHistories() throws Exception {
        // Initialize the database
        overtimeHistoryRepository.saveAndFlush(overtimeHistory);

        // Get all the overtimeHistoryList
        restOvertimeHistoryMockMvc.perform(get("/api/overtime-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overtimeHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].changeType").value(hasItem(DEFAULT_CHANGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].who").value(hasItem(DEFAULT_WHO.toString())));
    }
    
    @Test
    @Transactional
    public void getOvertimeHistory() throws Exception {
        // Initialize the database
        overtimeHistoryRepository.saveAndFlush(overtimeHistory);

        // Get the overtimeHistory
        restOvertimeHistoryMockMvc.perform(get("/api/overtime-histories/{id}", overtimeHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(overtimeHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.changeType").value(DEFAULT_CHANGE_TYPE.toString()))
            .andExpect(jsonPath("$.who").value(DEFAULT_WHO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOvertimeHistory() throws Exception {
        // Get the overtimeHistory
        restOvertimeHistoryMockMvc.perform(get("/api/overtime-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOvertimeHistory() throws Exception {
        // Initialize the database
        overtimeHistoryRepository.saveAndFlush(overtimeHistory);

        int databaseSizeBeforeUpdate = overtimeHistoryRepository.findAll().size();

        // Update the overtimeHistory
        OvertimeHistory updatedOvertimeHistory = overtimeHistoryRepository.findById(overtimeHistory.getId()).get();
        // Disconnect from session so that the updates on updatedOvertimeHistory are not directly saved in db
        em.detach(updatedOvertimeHistory);
        updatedOvertimeHistory
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .changeType(UPDATED_CHANGE_TYPE)
            .who(UPDATED_WHO);
        OvertimeHistoryDTO overtimeHistoryDTO = overtimeHistoryMapper.toDto(updatedOvertimeHistory);

        restOvertimeHistoryMockMvc.perform(put("/api/overtime-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the OvertimeHistory in the database
        List<OvertimeHistory> overtimeHistoryList = overtimeHistoryRepository.findAll();
        assertThat(overtimeHistoryList).hasSize(databaseSizeBeforeUpdate);
        OvertimeHistory testOvertimeHistory = overtimeHistoryList.get(overtimeHistoryList.size() - 1);
        assertThat(testOvertimeHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOvertimeHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOvertimeHistory.getChangeType()).isEqualTo(UPDATED_CHANGE_TYPE);
        assertThat(testOvertimeHistory.getWho()).isEqualTo(UPDATED_WHO);
    }

    @Test
    @Transactional
    public void updateNonExistingOvertimeHistory() throws Exception {
        int databaseSizeBeforeUpdate = overtimeHistoryRepository.findAll().size();

        // Create the OvertimeHistory
        OvertimeHistoryDTO overtimeHistoryDTO = overtimeHistoryMapper.toDto(overtimeHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOvertimeHistoryMockMvc.perform(put("/api/overtime-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OvertimeHistory in the database
        List<OvertimeHistory> overtimeHistoryList = overtimeHistoryRepository.findAll();
        assertThat(overtimeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOvertimeHistory() throws Exception {
        // Initialize the database
        overtimeHistoryRepository.saveAndFlush(overtimeHistory);

        int databaseSizeBeforeDelete = overtimeHistoryRepository.findAll().size();

        // Delete the overtimeHistory
        restOvertimeHistoryMockMvc.perform(delete("/api/overtime-histories/{id}", overtimeHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OvertimeHistory> overtimeHistoryList = overtimeHistoryRepository.findAll();
        assertThat(overtimeHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OvertimeHistory.class);
        OvertimeHistory overtimeHistory1 = new OvertimeHistory();
        overtimeHistory1.setId(1L);
        OvertimeHistory overtimeHistory2 = new OvertimeHistory();
        overtimeHistory2.setId(overtimeHistory1.getId());
        assertThat(overtimeHistory1).isEqualTo(overtimeHistory2);
        overtimeHistory2.setId(2L);
        assertThat(overtimeHistory1).isNotEqualTo(overtimeHistory2);
        overtimeHistory1.setId(null);
        assertThat(overtimeHistory1).isNotEqualTo(overtimeHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OvertimeHistoryDTO.class);
        OvertimeHistoryDTO overtimeHistoryDTO1 = new OvertimeHistoryDTO();
        overtimeHistoryDTO1.setId(1L);
        OvertimeHistoryDTO overtimeHistoryDTO2 = new OvertimeHistoryDTO();
        assertThat(overtimeHistoryDTO1).isNotEqualTo(overtimeHistoryDTO2);
        overtimeHistoryDTO2.setId(overtimeHistoryDTO1.getId());
        assertThat(overtimeHistoryDTO1).isEqualTo(overtimeHistoryDTO2);
        overtimeHistoryDTO2.setId(2L);
        assertThat(overtimeHistoryDTO1).isNotEqualTo(overtimeHistoryDTO2);
        overtimeHistoryDTO1.setId(null);
        assertThat(overtimeHistoryDTO1).isNotEqualTo(overtimeHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(overtimeHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(overtimeHistoryMapper.fromId(null)).isNull();
    }
}
