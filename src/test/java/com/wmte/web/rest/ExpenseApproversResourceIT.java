package com.wmte.web.rest;

import com.wmte.WmteApp;
import com.wmte.domain.ExpenseApprovers;
import com.wmte.repository.ExpenseApproversRepository;
import com.wmte.service.ExpenseApproversService;
import com.wmte.service.dto.ExpenseApproversDTO;
import com.wmte.service.mapper.ExpenseApproversMapper;
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
 * Integration tests for the {@link ExpenseApproversResource} REST controller.
 */
@SpringBootTest(classes = WmteApp.class)
public class ExpenseApproversResourceIT {

    @Autowired
    private ExpenseApproversRepository expenseApproversRepository;

    @Autowired
    private ExpenseApproversMapper expenseApproversMapper;

    @Autowired
    private ExpenseApproversService expenseApproversService;

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

    private MockMvc restExpenseApproversMockMvc;

    private ExpenseApprovers expenseApprovers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpenseApproversResource expenseApproversResource = new ExpenseApproversResource(expenseApproversService);
        this.restExpenseApproversMockMvc = MockMvcBuilders.standaloneSetup(expenseApproversResource)
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
    public static ExpenseApprovers createEntity(EntityManager em) {
        ExpenseApprovers expenseApprovers = new ExpenseApprovers();
        return expenseApprovers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpenseApprovers createUpdatedEntity(EntityManager em) {
        ExpenseApprovers expenseApprovers = new ExpenseApprovers();
        return expenseApprovers;
    }

    @BeforeEach
    public void initTest() {
        expenseApprovers = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpenseApprovers() throws Exception {
        int databaseSizeBeforeCreate = expenseApproversRepository.findAll().size();

        // Create the ExpenseApprovers
        ExpenseApproversDTO expenseApproversDTO = expenseApproversMapper.toDto(expenseApprovers);
        restExpenseApproversMockMvc.perform(post("/api/expense-approvers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseApproversDTO)))
            .andExpect(status().isCreated());

        // Validate the ExpenseApprovers in the database
        List<ExpenseApprovers> expenseApproversList = expenseApproversRepository.findAll();
        assertThat(expenseApproversList).hasSize(databaseSizeBeforeCreate + 1);
        ExpenseApprovers testExpenseApprovers = expenseApproversList.get(expenseApproversList.size() - 1);
    }

    @Test
    @Transactional
    public void createExpenseApproversWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expenseApproversRepository.findAll().size();

        // Create the ExpenseApprovers with an existing ID
        expenseApprovers.setId(1L);
        ExpenseApproversDTO expenseApproversDTO = expenseApproversMapper.toDto(expenseApprovers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpenseApproversMockMvc.perform(post("/api/expense-approvers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseApproversDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExpenseApprovers in the database
        List<ExpenseApprovers> expenseApproversList = expenseApproversRepository.findAll();
        assertThat(expenseApproversList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExpenseApprovers() throws Exception {
        // Initialize the database
        expenseApproversRepository.saveAndFlush(expenseApprovers);

        // Get all the expenseApproversList
        restExpenseApproversMockMvc.perform(get("/api/expense-approvers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenseApprovers.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getExpenseApprovers() throws Exception {
        // Initialize the database
        expenseApproversRepository.saveAndFlush(expenseApprovers);

        // Get the expenseApprovers
        restExpenseApproversMockMvc.perform(get("/api/expense-approvers/{id}", expenseApprovers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expenseApprovers.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExpenseApprovers() throws Exception {
        // Get the expenseApprovers
        restExpenseApproversMockMvc.perform(get("/api/expense-approvers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpenseApprovers() throws Exception {
        // Initialize the database
        expenseApproversRepository.saveAndFlush(expenseApprovers);

        int databaseSizeBeforeUpdate = expenseApproversRepository.findAll().size();

        // Update the expenseApprovers
        ExpenseApprovers updatedExpenseApprovers = expenseApproversRepository.findById(expenseApprovers.getId()).get();
        // Disconnect from session so that the updates on updatedExpenseApprovers are not directly saved in db
        em.detach(updatedExpenseApprovers);
        ExpenseApproversDTO expenseApproversDTO = expenseApproversMapper.toDto(updatedExpenseApprovers);

        restExpenseApproversMockMvc.perform(put("/api/expense-approvers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseApproversDTO)))
            .andExpect(status().isOk());

        // Validate the ExpenseApprovers in the database
        List<ExpenseApprovers> expenseApproversList = expenseApproversRepository.findAll();
        assertThat(expenseApproversList).hasSize(databaseSizeBeforeUpdate);
        ExpenseApprovers testExpenseApprovers = expenseApproversList.get(expenseApproversList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingExpenseApprovers() throws Exception {
        int databaseSizeBeforeUpdate = expenseApproversRepository.findAll().size();

        // Create the ExpenseApprovers
        ExpenseApproversDTO expenseApproversDTO = expenseApproversMapper.toDto(expenseApprovers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseApproversMockMvc.perform(put("/api/expense-approvers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseApproversDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExpenseApprovers in the database
        List<ExpenseApprovers> expenseApproversList = expenseApproversRepository.findAll();
        assertThat(expenseApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpenseApprovers() throws Exception {
        // Initialize the database
        expenseApproversRepository.saveAndFlush(expenseApprovers);

        int databaseSizeBeforeDelete = expenseApproversRepository.findAll().size();

        // Delete the expenseApprovers
        restExpenseApproversMockMvc.perform(delete("/api/expense-approvers/{id}", expenseApprovers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExpenseApprovers> expenseApproversList = expenseApproversRepository.findAll();
        assertThat(expenseApproversList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpenseApprovers.class);
        ExpenseApprovers expenseApprovers1 = new ExpenseApprovers();
        expenseApprovers1.setId(1L);
        ExpenseApprovers expenseApprovers2 = new ExpenseApprovers();
        expenseApprovers2.setId(expenseApprovers1.getId());
        assertThat(expenseApprovers1).isEqualTo(expenseApprovers2);
        expenseApprovers2.setId(2L);
        assertThat(expenseApprovers1).isNotEqualTo(expenseApprovers2);
        expenseApprovers1.setId(null);
        assertThat(expenseApprovers1).isNotEqualTo(expenseApprovers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpenseApproversDTO.class);
        ExpenseApproversDTO expenseApproversDTO1 = new ExpenseApproversDTO();
        expenseApproversDTO1.setId(1L);
        ExpenseApproversDTO expenseApproversDTO2 = new ExpenseApproversDTO();
        assertThat(expenseApproversDTO1).isNotEqualTo(expenseApproversDTO2);
        expenseApproversDTO2.setId(expenseApproversDTO1.getId());
        assertThat(expenseApproversDTO1).isEqualTo(expenseApproversDTO2);
        expenseApproversDTO2.setId(2L);
        assertThat(expenseApproversDTO1).isNotEqualTo(expenseApproversDTO2);
        expenseApproversDTO1.setId(null);
        assertThat(expenseApproversDTO1).isNotEqualTo(expenseApproversDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(expenseApproversMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(expenseApproversMapper.fromId(null)).isNull();
    }
}
