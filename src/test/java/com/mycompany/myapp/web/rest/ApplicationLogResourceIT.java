package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.SeatAllotmentApp;
import com.mycompany.myapp.domain.ApplicationLog;
import com.mycompany.myapp.repository.ApplicationLogRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ApplicationLogResource} REST controller.
 */
@SpringBootTest(classes = SeatAllotmentApp.class)
public class ApplicationLogResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ACTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ApplicationLogRepository applicationLogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restApplicationLogMockMvc;

    private ApplicationLog applicationLog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationLogResource applicationLogResource = new ApplicationLogResource(applicationLogRepository);
        this.restApplicationLogMockMvc = MockMvcBuilders.standaloneSetup(applicationLogResource)
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
    public static ApplicationLog createEntity() {
        ApplicationLog applicationLog = new ApplicationLog()
            .application_no(DEFAULT_APPLICATION_NO)
            .action(DEFAULT_ACTION)
            .action_by(DEFAULT_ACTION_BY)
            .action_date(DEFAULT_ACTION_DATE);
        return applicationLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationLog createUpdatedEntity() {
        ApplicationLog applicationLog = new ApplicationLog()
            .application_no(UPDATED_APPLICATION_NO)
            .action(UPDATED_ACTION)
            .action_by(UPDATED_ACTION_BY)
            .action_date(UPDATED_ACTION_DATE);
        return applicationLog;
    }

    @BeforeEach
    public void initTest() {
        applicationLogRepository.deleteAll();
        applicationLog = createEntity();
    }

    @Test
    public void createApplicationLog() throws Exception {
        int databaseSizeBeforeCreate = applicationLogRepository.findAll().size();

        // Create the ApplicationLog
        restApplicationLogMockMvc.perform(post("/api/application-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationLog)))
            .andExpect(status().isCreated());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationLog testApplicationLog = applicationLogList.get(applicationLogList.size() - 1);
        assertThat(testApplicationLog.getApplication_no()).isEqualTo(DEFAULT_APPLICATION_NO);
        assertThat(testApplicationLog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testApplicationLog.getAction_by()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testApplicationLog.getAction_date()).isEqualTo(DEFAULT_ACTION_DATE);
    }

    @Test
    public void createApplicationLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationLogRepository.findAll().size();

        // Create the ApplicationLog with an existing ID
        applicationLog.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationLogMockMvc.perform(post("/api/application-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationLog)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllApplicationLogs() throws Exception {
        // Initialize the database
        applicationLog.setId(UUID.randomUUID());
        applicationLogRepository.save(applicationLog);

        // Get all the applicationLogList
        restApplicationLogMockMvc.perform(get("/api/application-logs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationLog.getId().toString())))
            .andExpect(jsonPath("$.[*].application_no").value(hasItem(DEFAULT_APPLICATION_NO.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].action_by").value(hasItem(DEFAULT_ACTION_BY.toString())))
            .andExpect(jsonPath("$.[*].action_date").value(hasItem(sameInstant(DEFAULT_ACTION_DATE))));
    }
    
    @Test
    public void getApplicationLog() throws Exception {
        // Initialize the database
        applicationLog.setId(UUID.randomUUID());
        applicationLogRepository.save(applicationLog);

        // Get the applicationLog
        restApplicationLogMockMvc.perform(get("/api/application-logs/{id}", applicationLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationLog.getId().toString()))
            .andExpect(jsonPath("$.application_no").value(DEFAULT_APPLICATION_NO.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.action_by").value(DEFAULT_ACTION_BY.toString()))
            .andExpect(jsonPath("$.action_date").value(sameInstant(DEFAULT_ACTION_DATE)));
    }

    @Test
    public void getNonExistingApplicationLog() throws Exception {
        // Get the applicationLog
        restApplicationLogMockMvc.perform(get("/api/application-logs/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateApplicationLog() throws Exception {
        // Initialize the database
        applicationLog.setId(UUID.randomUUID());
        applicationLogRepository.save(applicationLog);

        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();

        // Update the applicationLog
        ApplicationLog updatedApplicationLog = applicationLogRepository.findById(applicationLog.getId()).get();
        updatedApplicationLog
            .application_no(UPDATED_APPLICATION_NO)
            .action(UPDATED_ACTION)
            .action_by(UPDATED_ACTION_BY)
            .action_date(UPDATED_ACTION_DATE);

        restApplicationLogMockMvc.perform(put("/api/application-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicationLog)))
            .andExpect(status().isOk());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
        ApplicationLog testApplicationLog = applicationLogList.get(applicationLogList.size() - 1);
        assertThat(testApplicationLog.getApplication_no()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testApplicationLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testApplicationLog.getAction_by()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testApplicationLog.getAction_date()).isEqualTo(UPDATED_ACTION_DATE);
    }

    @Test
    public void updateNonExistingApplicationLog() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();

        // Create the ApplicationLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationLogMockMvc.perform(put("/api/application-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationLog)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteApplicationLog() throws Exception {
        // Initialize the database
        applicationLog.setId(UUID.randomUUID());
        applicationLogRepository.save(applicationLog);

        int databaseSizeBeforeDelete = applicationLogRepository.findAll().size();

        // Delete the applicationLog
        restApplicationLogMockMvc.perform(delete("/api/application-logs/{id}", applicationLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationLog.class);
        ApplicationLog applicationLog1 = new ApplicationLog();
        applicationLog1.setId(UUID.randomUUID());
        ApplicationLog applicationLog2 = new ApplicationLog();
        applicationLog2.setId(applicationLog1.getId());
        assertThat(applicationLog1).isEqualTo(applicationLog2);
        applicationLog2.setId(UUID.randomUUID());
        assertThat(applicationLog1).isNotEqualTo(applicationLog2);
        applicationLog1.setId(null);
        assertThat(applicationLog1).isNotEqualTo(applicationLog2);
    }
}
