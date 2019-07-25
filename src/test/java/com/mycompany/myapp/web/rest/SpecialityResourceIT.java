package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.SeatAllotmentApp;
import com.mycompany.myapp.domain.Speciality;
import com.mycompany.myapp.repository.SpecialityRepository;
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
 * Integration tests for the {@Link SpecialityResource} REST controller.
 */
@SpringBootTest(classes = SeatAllotmentApp.class)
public class SpecialityResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSpecialityMockMvc;

    private Speciality speciality;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialityResource specialityResource = new SpecialityResource(specialityRepository);
        this.restSpecialityMockMvc = MockMvcBuilders.standaloneSetup(specialityResource)
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
    public static Speciality createEntity() {
        Speciality speciality = new Speciality()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE);
        return speciality;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speciality createUpdatedEntity() {
        Speciality speciality = new Speciality()
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE);
        return speciality;
    }

    @BeforeEach
    public void initTest() {
        specialityRepository.deleteAll();
        speciality = createEntity();
    }

    @Test
    public void createSpeciality() throws Exception {
        int databaseSizeBeforeCreate = specialityRepository.findAll().size();

        // Create the Speciality
        restSpecialityMockMvc.perform(post("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciality)))
            .andExpect(status().isCreated());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeCreate + 1);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpeciality.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void createSpecialityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialityRepository.findAll().size();

        // Create the Speciality with an existing ID
        speciality.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialityMockMvc.perform(post("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciality)))
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllSpecialities() throws Exception {
        // Initialize the database
        speciality.setId(UUID.randomUUID());
        specialityRepository.save(speciality);

        // Get all the specialityList
        restSpecialityMockMvc.perform(get("/api/specialities"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speciality.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getSpeciality() throws Exception {
        // Initialize the database
        speciality.setId(UUID.randomUUID());
        specialityRepository.save(speciality);

        // Get the speciality
        restSpecialityMockMvc.perform(get("/api/specialities/{id}", speciality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speciality.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    public void getNonExistingSpeciality() throws Exception {
        // Get the speciality
        restSpecialityMockMvc.perform(get("/api/specialities/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSpeciality() throws Exception {
        // Initialize the database
        speciality.setId(UUID.randomUUID());
        specialityRepository.save(speciality);

        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Update the speciality
        Speciality updatedSpeciality = specialityRepository.findById(speciality.getId()).get();
        updatedSpeciality
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE);

        restSpecialityMockMvc.perform(put("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpeciality)))
            .andExpect(status().isOk());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpeciality.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void updateNonExistingSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Create the Speciality

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialityMockMvc.perform(put("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciality)))
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSpeciality() throws Exception {
        // Initialize the database
        speciality.setId(UUID.randomUUID());
        specialityRepository.save(speciality);

        int databaseSizeBeforeDelete = specialityRepository.findAll().size();

        // Delete the speciality
        restSpecialityMockMvc.perform(delete("/api/specialities/{id}", speciality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speciality.class);
        Speciality speciality1 = new Speciality();
        speciality1.setId(UUID.randomUUID());
        Speciality speciality2 = new Speciality();
        speciality2.setId(speciality1.getId());
        assertThat(speciality1).isEqualTo(speciality2);
        speciality2.setId(UUID.randomUUID());
        assertThat(speciality1).isNotEqualTo(speciality2);
        speciality1.setId(null);
        assertThat(speciality1).isNotEqualTo(speciality2);
    }
}
