package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.SeatAllotmentApp;
import com.mycompany.myapp.domain.Applicationlog;
import com.mycompany.myapp.repository.ApplicationlogRepository;
import com.mycompany.myapp.service.ApplicationlogService;
import com.mycompany.myapp.service.dto.ApplicationlogDTO;
import com.mycompany.myapp.service.mapper.ApplicationlogMapper;
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
 * Integration tests for the {@Link ApplicationlogResource} REST controller.
 */
@SpringBootTest(classes = SeatAllotmentApp.class)
public class ApplicationlogResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ACTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ApplicationlogRepository applicationlogRepository;

    @Autowired
    private ApplicationlogMapper applicationlogMapper;

    @Autowired
    private ApplicationlogService applicationlogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restApplicationlogMockMvc;

    private Applicationlog applicationlog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationlogResource applicationlogResource = new ApplicationlogResource(applicationlogService);
        this.restApplicationlogMockMvc = MockMvcBuilders.standaloneSetup(applicationlogResource)
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
    public static Applicationlog createEntity() {
        Applicationlog applicationlog = new Applicationlog()
            .application_no(DEFAULT_APPLICATION_NO)
            .action(DEFAULT_ACTION)
            .action_by(DEFAULT_ACTION_BY)
            .action_date(DEFAULT_ACTION_DATE);
        return applicationlog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicationlog createUpdatedEntity() {
        Applicationlog applicationlog = new Applicationlog()
            .application_no(UPDATED_APPLICATION_NO)
            .action(UPDATED_ACTION)
            .action_by(UPDATED_ACTION_BY)
            .action_date(UPDATED_ACTION_DATE);
        return applicationlog;
    }

    @BeforeEach
    public void initTest() {
        applicationlogRepository.deleteAll();
        applicationlog = createEntity();
    }

    @Test
    public void createApplicationlog() throws Exception {
        int databaseSizeBeforeCreate = applicationlogRepository.findAll().size();

        // Create the Applicationlog
        ApplicationlogDTO applicationlogDTO = applicationlogMapper.toDto(applicationlog);
        restApplicationlogMockMvc.perform(post("/api/applicationlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationlogDTO)))
            .andExpect(status().isCreated());

        // Validate the Applicationlog in the database
        List<Applicationlog> applicationlogList = applicationlogRepository.findAll();
        assertThat(applicationlogList).hasSize(databaseSizeBeforeCreate + 1);
        Applicationlog testApplicationlog = applicationlogList.get(applicationlogList.size() - 1);
        assertThat(testApplicationlog.getApplication_no()).isEqualTo(DEFAULT_APPLICATION_NO);
        assertThat(testApplicationlog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testApplicationlog.getAction_by()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testApplicationlog.getAction_date()).isEqualTo(DEFAULT_ACTION_DATE);
    }

    @Test
    public void createApplicationlogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationlogRepository.findAll().size();

        // Create the Applicationlog with an existing ID
        applicationlog.setId(UUID.randomUUID());
        ApplicationlogDTO applicationlogDTO = applicationlogMapper.toDto(applicationlog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationlogMockMvc.perform(post("/api/applicationlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationlogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Applicationlog in the database
        List<Applicationlog> applicationlogList = applicationlogRepository.findAll();
        assertThat(applicationlogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllApplicationlogs() throws Exception {
        // Initialize the database
        applicationlog.setId(UUID.randomUUID());
        applicationlogRepository.save(applicationlog);

        // Get all the applicationlogList
        restApplicationlogMockMvc.perform(get("/api/applicationlogs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationlog.getId().toString())))
            .andExpect(jsonPath("$.[*].application_no").value(hasItem(DEFAULT_APPLICATION_NO.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].action_by").value(hasItem(DEFAULT_ACTION_BY.toString())))
            .andExpect(jsonPath("$.[*].action_date").value(hasItem(sameInstant(DEFAULT_ACTION_DATE))));
    }
    
    @Test
    public void getApplicationlog() throws Exception {
        // Initialize the database
        applicationlog.setId(UUID.randomUUID());
        applicationlogRepository.save(applicationlog);

        // Get the applicationlog
        restApplicationlogMockMvc.perform(get("/api/applicationlogs/{id}", applicationlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationlog.getId().toString()))
            .andExpect(jsonPath("$.application_no").value(DEFAULT_APPLICATION_NO.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.action_by").value(DEFAULT_ACTION_BY.toString()))
            .andExpect(jsonPath("$.action_date").value(sameInstant(DEFAULT_ACTION_DATE)));
    }

    @Test
    public void getNonExistingApplicationlog() throws Exception {
        // Get the applicationlog
        restApplicationlogMockMvc.perform(get("/api/applicationlogs/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateApplicationlog() throws Exception {
        // Initialize the database
        applicationlog.setId(UUID.randomUUID());
        applicationlogRepository.save(applicationlog);

        int databaseSizeBeforeUpdate = applicationlogRepository.findAll().size();

        // Update the applicationlog
        Applicationlog updatedApplicationlog = applicationlogRepository.findById(applicationlog.getId()).get();
        updatedApplicationlog
            .application_no(UPDATED_APPLICATION_NO)
            .action(UPDATED_ACTION)
            .action_by(UPDATED_ACTION_BY)
            .action_date(UPDATED_ACTION_DATE);
        ApplicationlogDTO applicationlogDTO = applicationlogMapper.toDto(updatedApplicationlog);

        restApplicationlogMockMvc.perform(put("/api/applicationlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationlogDTO)))
            .andExpect(status().isOk());

        // Validate the Applicationlog in the database
        List<Applicationlog> applicationlogList = applicationlogRepository.findAll();
        assertThat(applicationlogList).hasSize(databaseSizeBeforeUpdate);
        Applicationlog testApplicationlog = applicationlogList.get(applicationlogList.size() - 1);
        assertThat(testApplicationlog.getApplication_no()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testApplicationlog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testApplicationlog.getAction_by()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testApplicationlog.getAction_date()).isEqualTo(UPDATED_ACTION_DATE);
    }

    @Test
    public void updateNonExistingApplicationlog() throws Exception {
        int databaseSizeBeforeUpdate = applicationlogRepository.findAll().size();

        // Create the Applicationlog
        ApplicationlogDTO applicationlogDTO = applicationlogMapper.toDto(applicationlog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationlogMockMvc.perform(put("/api/applicationlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationlogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Applicationlog in the database
        List<Applicationlog> applicationlogList = applicationlogRepository.findAll();
        assertThat(applicationlogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteApplicationlog() throws Exception {
        // Initialize the database
        applicationlog.setId(UUID.randomUUID());
        applicationlogRepository.save(applicationlog);

        int databaseSizeBeforeDelete = applicationlogRepository.findAll().size();

        // Delete the applicationlog
        restApplicationlogMockMvc.perform(delete("/api/applicationlogs/{id}", applicationlog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Applicationlog> applicationlogList = applicationlogRepository.findAll();
        assertThat(applicationlogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Applicationlog.class);
        Applicationlog applicationlog1 = new Applicationlog();
        applicationlog1.setId(UUID.randomUUID());
        Applicationlog applicationlog2 = new Applicationlog();
        applicationlog2.setId(applicationlog1.getId());
        assertThat(applicationlog1).isEqualTo(applicationlog2);
        applicationlog2.setId(UUID.randomUUID());
        assertThat(applicationlog1).isNotEqualTo(applicationlog2);
        applicationlog1.setId(null);
        assertThat(applicationlog1).isNotEqualTo(applicationlog2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationlogDTO.class);
        ApplicationlogDTO applicationlogDTO1 = new ApplicationlogDTO();
        applicationlogDTO1.setId(UUID.randomUUID());
        ApplicationlogDTO applicationlogDTO2 = new ApplicationlogDTO();
        assertThat(applicationlogDTO1).isNotEqualTo(applicationlogDTO2);
        applicationlogDTO2.setId(applicationlogDTO1.getId());
        assertThat(applicationlogDTO1).isEqualTo(applicationlogDTO2);
        applicationlogDTO2.setId(UUID.randomUUID());
        assertThat(applicationlogDTO1).isNotEqualTo(applicationlogDTO2);
        applicationlogDTO1.setId(null);
        assertThat(applicationlogDTO1).isNotEqualTo(applicationlogDTO2);
    }
}
