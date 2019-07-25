package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.SeatAllotmentApp;
import com.mycompany.myapp.domain.Institute;
import com.mycompany.myapp.repository.InstituteRepository;
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


import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link InstituteResource} REST controller.
 */
@SpringBootTest(classes = SeatAllotmentApp.class)
public class InstituteResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restInstituteMockMvc;

    private Institute institute;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstituteResource instituteResource = new InstituteResource(instituteRepository);
        this.restInstituteMockMvc = MockMvcBuilders.standaloneSetup(instituteResource)
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
    public static Institute createEntity() {
        Institute institute = new Institute()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE);
        return institute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institute createUpdatedEntity() {
        Institute institute = new Institute()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);
        return institute;
    }

    @BeforeEach
    public void initTest() {
        instituteRepository.deleteAll();
        institute = createEntity();
    }

    @Test
    public void createInstitute() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();

        // Create the Institute
        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institute)))
            .andExpect(status().isCreated());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeCreate + 1);
        Institute testInstitute = instituteList.get(instituteList.size() - 1);
        assertThat(testInstitute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstitute.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createInstituteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();

        // Create the Institute with an existing ID
        institute.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institute)))
            .andExpect(status().isBadRequest());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllInstitutes() throws Exception {
        // Initialize the database
        institute.setId(UUID.randomUUID());
        instituteRepository.save(institute);

        // Get all the instituteList
        restInstituteMockMvc.perform(get("/api/institutes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institute.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    public void getInstitute() throws Exception {
        // Initialize the database
        institute.setId(UUID.randomUUID());
        instituteRepository.save(institute);

        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", institute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(institute.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingInstitute() throws Exception {
        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInstitute() throws Exception {
        // Initialize the database
        institute.setId(UUID.randomUUID());
        instituteRepository.save(institute);

        int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Update the institute
        Institute updatedInstitute = instituteRepository.findById(institute.getId()).get();
        updatedInstitute
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);

        restInstituteMockMvc.perform(put("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstitute)))
            .andExpect(status().isOk());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeUpdate);
        Institute testInstitute = instituteList.get(instituteList.size() - 1);
        assertThat(testInstitute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstitute.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingInstitute() throws Exception {
        int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Create the Institute

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituteMockMvc.perform(put("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institute)))
            .andExpect(status().isBadRequest());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteInstitute() throws Exception {
        // Initialize the database
        institute.setId(UUID.randomUUID());
        instituteRepository.save(institute);

        int databaseSizeBeforeDelete = instituteRepository.findAll().size();

        // Delete the institute
        restInstituteMockMvc.perform(delete("/api/institutes/{id}", institute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Institute.class);
        Institute institute1 = new Institute();
        institute1.setId(UUID.randomUUID());
        Institute institute2 = new Institute();
        institute2.setId(institute1.getId());
        assertThat(institute1).isEqualTo(institute2);
        institute2.setId(UUID.randomUUID());
        assertThat(institute1).isNotEqualTo(institute2);
        institute1.setId(null);
        assertThat(institute1).isNotEqualTo(institute2);
    }
}
