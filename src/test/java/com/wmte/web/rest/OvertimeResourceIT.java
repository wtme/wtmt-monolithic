package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.Overtime;
import com.wmte.repository.OvertimeRepository;
import com.wmte.service.OvertimeService;
import com.wmte.service.dto.OvertimeDTO;
import com.wmte.service.mapper.OvertimeMapper;
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
import java.math.BigDecimal;
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
 * Integration tests for the {@link OvertimeResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class OvertimeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final OvertimeStatus DEFAULT_STATUS = OvertimeStatus.OPEN;
    private static final OvertimeStatus UPDATED_STATUS = OvertimeStatus.APPROVED;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FROM_DATE = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_TO_DATE = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_DATE = Instant.ofEpochMilli(-1L);

    private static final BigDecimal DEFAULT_OVERTIME_IN_HOURS = new BigDecimal(1);
    private static final BigDecimal UPDATED_OVERTIME_IN_HOURS = new BigDecimal(2);
    private static final BigDecimal SMALLER_OVERTIME_IN_HOURS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_BILLABLE_HOURS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_BILLABLE_HOURS = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_BILLABLE_HOURS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_COSTING_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COSTING_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_COSTING_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_CODE = "BBBBBBBBBB";

    @Autowired
    private OvertimeRepository overtimeRepository;

    @Autowired
    private OvertimeMapper overtimeMapper;

    @Autowired
    private OvertimeService overtimeService;

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

    private MockMvc restOvertimeMockMvc;

    private Overtime overtime;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OvertimeResource overtimeResource = new OvertimeResource(overtimeService);
        this.restOvertimeMockMvc = MockMvcBuilders.standaloneSetup(overtimeResource)
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
    public static Overtime createEntity(EntityManager em) {
        Overtime overtime = new Overtime()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .details(DEFAULT_DETAILS)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .overtimeInHours(DEFAULT_OVERTIME_IN_HOURS)
            .totalBillableHours(DEFAULT_TOTAL_BILLABLE_HOURS)
            .totalCostingAmount(DEFAULT_TOTAL_COSTING_AMOUNT)
            .note(DEFAULT_NOTE)
            .location(DEFAULT_LOCATION)
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .employeeEmail(DEFAULT_EMPLOYEE_EMAIL)
            .departmentName(DEFAULT_DEPARTMENT_NAME)
            .departmentCode(DEFAULT_DEPARTMENT_CODE);
        return overtime;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overtime createUpdatedEntity(EntityManager em) {
        Overtime overtime = new Overtime()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .details(UPDATED_DETAILS)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .overtimeInHours(UPDATED_OVERTIME_IN_HOURS)
            .totalBillableHours(UPDATED_TOTAL_BILLABLE_HOURS)
            .totalCostingAmount(UPDATED_TOTAL_COSTING_AMOUNT)
            .note(UPDATED_NOTE)
            .location(UPDATED_LOCATION)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL)
            .departmentName(UPDATED_DEPARTMENT_NAME)
            .departmentCode(UPDATED_DEPARTMENT_CODE);
        return overtime;
    }

    @BeforeEach
    public void initTest() {
        overtime = createEntity(em);
    }

    @Test
    @Transactional
    public void createOvertime() throws Exception {
        int databaseSizeBeforeCreate = overtimeRepository.findAll().size();

        // Create the Overtime
        OvertimeDTO overtimeDTO = overtimeMapper.toDto(overtime);
        restOvertimeMockMvc.perform(post("/api/overtimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeDTO)))
            .andExpect(status().isCreated());

        // Validate the Overtime in the database
        List<Overtime> overtimeList = overtimeRepository.findAll();
        assertThat(overtimeList).hasSize(databaseSizeBeforeCreate + 1);
        Overtime testOvertime = overtimeList.get(overtimeList.size() - 1);
        assertThat(testOvertime.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOvertime.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOvertime.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOvertime.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testOvertime.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testOvertime.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testOvertime.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOvertime.getOvertimeInHours()).isEqualTo(DEFAULT_OVERTIME_IN_HOURS);
        assertThat(testOvertime.getTotalBillableHours()).isEqualTo(DEFAULT_TOTAL_BILLABLE_HOURS);
        assertThat(testOvertime.getTotalCostingAmount()).isEqualTo(DEFAULT_TOTAL_COSTING_AMOUNT);
        assertThat(testOvertime.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testOvertime.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testOvertime.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testOvertime.getEmployeeEmail()).isEqualTo(DEFAULT_EMPLOYEE_EMAIL);
        assertThat(testOvertime.getDepartmentName()).isEqualTo(DEFAULT_DEPARTMENT_NAME);
        assertThat(testOvertime.getDepartmentCode()).isEqualTo(DEFAULT_DEPARTMENT_CODE);
    }

    @Test
    @Transactional
    public void createOvertimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = overtimeRepository.findAll().size();

        // Create the Overtime with an existing ID
        overtime.setId(1L);
        OvertimeDTO overtimeDTO = overtimeMapper.toDto(overtime);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOvertimeMockMvc.perform(post("/api/overtimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Overtime in the database
        List<Overtime> overtimeList = overtimeRepository.findAll();
        assertThat(overtimeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = overtimeRepository.findAll().size();
        // set the field null
        overtime.setCode(null);

        // Create the Overtime, which fails.
        OvertimeDTO overtimeDTO = overtimeMapper.toDto(overtime);

        restOvertimeMockMvc.perform(post("/api/overtimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeDTO)))
            .andExpect(status().isBadRequest());

        List<Overtime> overtimeList = overtimeRepository.findAll();
        assertThat(overtimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = overtimeRepository.findAll().size();
        // set the field null
        overtime.setStatus(null);

        // Create the Overtime, which fails.
        OvertimeDTO overtimeDTO = overtimeMapper.toDto(overtime);

        restOvertimeMockMvc.perform(post("/api/overtimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeDTO)))
            .andExpect(status().isBadRequest());

        List<Overtime> overtimeList = overtimeRepository.findAll();
        assertThat(overtimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOvertimes() throws Exception {
        // Initialize the database
        overtimeRepository.saveAndFlush(overtime);

        // Get all the overtimeList
        restOvertimeMockMvc.perform(get("/api/overtimes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overtime.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].overtimeInHours").value(hasItem(DEFAULT_OVERTIME_IN_HOURS.intValue())))
            .andExpect(jsonPath("$.[*].totalBillableHours").value(hasItem(DEFAULT_TOTAL_BILLABLE_HOURS.intValue())))
            .andExpect(jsonPath("$.[*].totalCostingAmount").value(hasItem(DEFAULT_TOTAL_COSTING_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].employeeEmail").value(hasItem(DEFAULT_EMPLOYEE_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].departmentName").value(hasItem(DEFAULT_DEPARTMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].departmentCode").value(hasItem(DEFAULT_DEPARTMENT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getOvertime() throws Exception {
        // Initialize the database
        overtimeRepository.saveAndFlush(overtime);

        // Get the overtime
        restOvertimeMockMvc.perform(get("/api/overtimes/{id}", overtime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(overtime.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.overtimeInHours").value(DEFAULT_OVERTIME_IN_HOURS.intValue()))
            .andExpect(jsonPath("$.totalBillableHours").value(DEFAULT_TOTAL_BILLABLE_HOURS.intValue()))
            .andExpect(jsonPath("$.totalCostingAmount").value(DEFAULT_TOTAL_COSTING_AMOUNT.intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
            .andExpect(jsonPath("$.employeeEmail").value(DEFAULT_EMPLOYEE_EMAIL.toString()))
            .andExpect(jsonPath("$.departmentName").value(DEFAULT_DEPARTMENT_NAME.toString()))
            .andExpect(jsonPath("$.departmentCode").value(DEFAULT_DEPARTMENT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOvertime() throws Exception {
        // Get the overtime
        restOvertimeMockMvc.perform(get("/api/overtimes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOvertime() throws Exception {
        // Initialize the database
        overtimeRepository.saveAndFlush(overtime);

        int databaseSizeBeforeUpdate = overtimeRepository.findAll().size();

        // Update the overtime
        Overtime updatedOvertime = overtimeRepository.findById(overtime.getId()).get();
        // Disconnect from session so that the updates on updatedOvertime are not directly saved in db
        em.detach(updatedOvertime);
        updatedOvertime
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .details(UPDATED_DETAILS)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .overtimeInHours(UPDATED_OVERTIME_IN_HOURS)
            .totalBillableHours(UPDATED_TOTAL_BILLABLE_HOURS)
            .totalCostingAmount(UPDATED_TOTAL_COSTING_AMOUNT)
            .note(UPDATED_NOTE)
            .location(UPDATED_LOCATION)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL)
            .departmentName(UPDATED_DEPARTMENT_NAME)
            .departmentCode(UPDATED_DEPARTMENT_CODE);
        OvertimeDTO overtimeDTO = overtimeMapper.toDto(updatedOvertime);

        restOvertimeMockMvc.perform(put("/api/overtimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeDTO)))
            .andExpect(status().isOk());

        // Validate the Overtime in the database
        List<Overtime> overtimeList = overtimeRepository.findAll();
        assertThat(overtimeList).hasSize(databaseSizeBeforeUpdate);
        Overtime testOvertime = overtimeList.get(overtimeList.size() - 1);
        assertThat(testOvertime.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOvertime.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOvertime.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOvertime.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testOvertime.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testOvertime.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testOvertime.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOvertime.getOvertimeInHours()).isEqualTo(UPDATED_OVERTIME_IN_HOURS);
        assertThat(testOvertime.getTotalBillableHours()).isEqualTo(UPDATED_TOTAL_BILLABLE_HOURS);
        assertThat(testOvertime.getTotalCostingAmount()).isEqualTo(UPDATED_TOTAL_COSTING_AMOUNT);
        assertThat(testOvertime.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testOvertime.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testOvertime.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testOvertime.getEmployeeEmail()).isEqualTo(UPDATED_EMPLOYEE_EMAIL);
        assertThat(testOvertime.getDepartmentName()).isEqualTo(UPDATED_DEPARTMENT_NAME);
        assertThat(testOvertime.getDepartmentCode()).isEqualTo(UPDATED_DEPARTMENT_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingOvertime() throws Exception {
        int databaseSizeBeforeUpdate = overtimeRepository.findAll().size();

        // Create the Overtime
        OvertimeDTO overtimeDTO = overtimeMapper.toDto(overtime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOvertimeMockMvc.perform(put("/api/overtimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overtimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Overtime in the database
        List<Overtime> overtimeList = overtimeRepository.findAll();
        assertThat(overtimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOvertime() throws Exception {
        // Initialize the database
        overtimeRepository.saveAndFlush(overtime);

        int databaseSizeBeforeDelete = overtimeRepository.findAll().size();

        // Delete the overtime
        restOvertimeMockMvc.perform(delete("/api/overtimes/{id}", overtime.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Overtime> overtimeList = overtimeRepository.findAll();
        assertThat(overtimeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overtime.class);
        Overtime overtime1 = new Overtime();
        overtime1.setId(1L);
        Overtime overtime2 = new Overtime();
        overtime2.setId(overtime1.getId());
        assertThat(overtime1).isEqualTo(overtime2);
        overtime2.setId(2L);
        assertThat(overtime1).isNotEqualTo(overtime2);
        overtime1.setId(null);
        assertThat(overtime1).isNotEqualTo(overtime2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OvertimeDTO.class);
        OvertimeDTO overtimeDTO1 = new OvertimeDTO();
        overtimeDTO1.setId(1L);
        OvertimeDTO overtimeDTO2 = new OvertimeDTO();
        assertThat(overtimeDTO1).isNotEqualTo(overtimeDTO2);
        overtimeDTO2.setId(overtimeDTO1.getId());
        assertThat(overtimeDTO1).isEqualTo(overtimeDTO2);
        overtimeDTO2.setId(2L);
        assertThat(overtimeDTO1).isNotEqualTo(overtimeDTO2);
        overtimeDTO1.setId(null);
        assertThat(overtimeDTO1).isNotEqualTo(overtimeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(overtimeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(overtimeMapper.fromId(null)).isNull();
    }
}
