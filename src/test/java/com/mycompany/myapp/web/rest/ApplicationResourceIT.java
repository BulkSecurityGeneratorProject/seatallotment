package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.SeatAllotmentApp;
import com.mycompany.myapp.domain.Application;
import com.mycompany.myapp.repository.ApplicationRepository;
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
 * Integration tests for the {@Link ApplicationResource} REST controller.
 */
@SpringBootTest(classes = SeatAllotmentApp.class)
public class ApplicationResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICANT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICANT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_MOBILE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_APPLICANT_DOB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_APPLICANT_DOB = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_APPLICANT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTITUTE = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALITY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITY = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_DOC = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_PREFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_PREFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_PREFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_PREFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_PREFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_PREFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRYBY = "AAAAAAAAAA";
    private static final String UPDATED_ENTRYBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ENTRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENTRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restApplicationMockMvc;

    private Application application;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationResource applicationResource = new ApplicationResource(applicationRepository);
        this.restApplicationMockMvc = MockMvcBuilders.standaloneSetup(applicationResource)
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
    public static Application createEntity() {
        Application application = new Application()
            .application_no(DEFAULT_APPLICATION_NO)
            .applicant_name(DEFAULT_APPLICANT_NAME)
            .applicant_email(DEFAULT_APPLICANT_EMAIL)
            .applicant_mobile(DEFAULT_APPLICANT_MOBILE)
            .applicant_dob(DEFAULT_APPLICANT_DOB)
            .applicant_address(DEFAULT_APPLICANT_ADDRESS)
            .course(DEFAULT_COURSE)
            .institute(DEFAULT_INSTITUTE)
            .speciality(DEFAULT_SPECIALITY)
            .course_doc(DEFAULT_COURSE_DOC)
            .first_preference(DEFAULT_FIRST_PREFERENCE)
            .second_preference(DEFAULT_SECOND_PREFERENCE)
            .third_preference(DEFAULT_THIRD_PREFERENCE)
            .entryby(DEFAULT_ENTRYBY)
            .entry_date(DEFAULT_ENTRY_DATE)
            .status(DEFAULT_STATUS);
        return application;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createUpdatedEntity() {
        Application application = new Application()
            .application_no(UPDATED_APPLICATION_NO)
            .applicant_name(UPDATED_APPLICANT_NAME)
            .applicant_email(UPDATED_APPLICANT_EMAIL)
            .applicant_mobile(UPDATED_APPLICANT_MOBILE)
            .applicant_dob(UPDATED_APPLICANT_DOB)
            .applicant_address(UPDATED_APPLICANT_ADDRESS)
            .course(UPDATED_COURSE)
            .institute(UPDATED_INSTITUTE)
            .speciality(UPDATED_SPECIALITY)
            .course_doc(UPDATED_COURSE_DOC)
            .first_preference(UPDATED_FIRST_PREFERENCE)
            .second_preference(UPDATED_SECOND_PREFERENCE)
            .third_preference(UPDATED_THIRD_PREFERENCE)
            .entryby(UPDATED_ENTRYBY)
            .entry_date(UPDATED_ENTRY_DATE)
            .status(UPDATED_STATUS);
        return application;
    }

    @BeforeEach
    public void initTest() {
        applicationRepository.deleteAll();
        application = createEntity();
    }

    @Test
    public void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getApplication_no()).isEqualTo(DEFAULT_APPLICATION_NO);
        assertThat(testApplication.getApplicant_name()).isEqualTo(DEFAULT_APPLICANT_NAME);
        assertThat(testApplication.getApplicant_email()).isEqualTo(DEFAULT_APPLICANT_EMAIL);
        assertThat(testApplication.getApplicant_mobile()).isEqualTo(DEFAULT_APPLICANT_MOBILE);
        assertThat(testApplication.getApplicant_dob()).isEqualTo(DEFAULT_APPLICANT_DOB);
        assertThat(testApplication.getApplicant_address()).isEqualTo(DEFAULT_APPLICANT_ADDRESS);
        assertThat(testApplication.getCourse()).isEqualTo(DEFAULT_COURSE);
        assertThat(testApplication.getInstitute()).isEqualTo(DEFAULT_INSTITUTE);
        assertThat(testApplication.getSpeciality()).isEqualTo(DEFAULT_SPECIALITY);
        assertThat(testApplication.getCourse_doc()).isEqualTo(DEFAULT_COURSE_DOC);
        assertThat(testApplication.getFirst_preference()).isEqualTo(DEFAULT_FIRST_PREFERENCE);
        assertThat(testApplication.getSecond_preference()).isEqualTo(DEFAULT_SECOND_PREFERENCE);
        assertThat(testApplication.getThird_preference()).isEqualTo(DEFAULT_THIRD_PREFERENCE);
        assertThat(testApplication.getEntryby()).isEqualTo(DEFAULT_ENTRYBY);
        assertThat(testApplication.getEntry_date()).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application with an existing ID
        application.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllApplications() throws Exception {
        // Initialize the database
        application.setId(UUID.randomUUID());
        applicationRepository.save(application);

        // Get all the applicationList
        restApplicationMockMvc.perform(get("/api/applications"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().toString())))
            .andExpect(jsonPath("$.[*].application_no").value(hasItem(DEFAULT_APPLICATION_NO.toString())))
            .andExpect(jsonPath("$.[*].applicant_name").value(hasItem(DEFAULT_APPLICANT_NAME.toString())))
            .andExpect(jsonPath("$.[*].applicant_email").value(hasItem(DEFAULT_APPLICANT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].applicant_mobile").value(hasItem(DEFAULT_APPLICANT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].applicant_dob").value(hasItem(sameInstant(DEFAULT_APPLICANT_DOB))))
            .andExpect(jsonPath("$.[*].applicant_address").value(hasItem(DEFAULT_APPLICANT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE.toString())))
            .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE.toString())))
            .andExpect(jsonPath("$.[*].speciality").value(hasItem(DEFAULT_SPECIALITY.toString())))
            .andExpect(jsonPath("$.[*].course_doc").value(hasItem(DEFAULT_COURSE_DOC.toString())))
            .andExpect(jsonPath("$.[*].first_preference").value(hasItem(DEFAULT_FIRST_PREFERENCE.toString())))
            .andExpect(jsonPath("$.[*].second_preference").value(hasItem(DEFAULT_SECOND_PREFERENCE.toString())))
            .andExpect(jsonPath("$.[*].third_preference").value(hasItem(DEFAULT_THIRD_PREFERENCE.toString())))
            .andExpect(jsonPath("$.[*].entryby").value(hasItem(DEFAULT_ENTRYBY.toString())))
            .andExpect(jsonPath("$.[*].entry_date").value(hasItem(sameInstant(DEFAULT_ENTRY_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    public void getApplication() throws Exception {
        // Initialize the database
        application.setId(UUID.randomUUID());
        applicationRepository.save(application);

        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().toString()))
            .andExpect(jsonPath("$.application_no").value(DEFAULT_APPLICATION_NO.toString()))
            .andExpect(jsonPath("$.applicant_name").value(DEFAULT_APPLICANT_NAME.toString()))
            .andExpect(jsonPath("$.applicant_email").value(DEFAULT_APPLICANT_EMAIL.toString()))
            .andExpect(jsonPath("$.applicant_mobile").value(DEFAULT_APPLICANT_MOBILE.toString()))
            .andExpect(jsonPath("$.applicant_dob").value(sameInstant(DEFAULT_APPLICANT_DOB)))
            .andExpect(jsonPath("$.applicant_address").value(DEFAULT_APPLICANT_ADDRESS.toString()))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE.toString()))
            .andExpect(jsonPath("$.institute").value(DEFAULT_INSTITUTE.toString()))
            .andExpect(jsonPath("$.speciality").value(DEFAULT_SPECIALITY.toString()))
            .andExpect(jsonPath("$.course_doc").value(DEFAULT_COURSE_DOC.toString()))
            .andExpect(jsonPath("$.first_preference").value(DEFAULT_FIRST_PREFERENCE.toString()))
            .andExpect(jsonPath("$.second_preference").value(DEFAULT_SECOND_PREFERENCE.toString()))
            .andExpect(jsonPath("$.third_preference").value(DEFAULT_THIRD_PREFERENCE.toString()))
            .andExpect(jsonPath("$.entryby").value(DEFAULT_ENTRYBY.toString()))
            .andExpect(jsonPath("$.entry_date").value(sameInstant(DEFAULT_ENTRY_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    public void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateApplication() throws Exception {
        // Initialize the database
        application.setId(UUID.randomUUID());
        applicationRepository.save(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Application updatedApplication = applicationRepository.findById(application.getId()).get();
        updatedApplication
            .application_no(UPDATED_APPLICATION_NO)
            .applicant_name(UPDATED_APPLICANT_NAME)
            .applicant_email(UPDATED_APPLICANT_EMAIL)
            .applicant_mobile(UPDATED_APPLICANT_MOBILE)
            .applicant_dob(UPDATED_APPLICANT_DOB)
            .applicant_address(UPDATED_APPLICANT_ADDRESS)
            .course(UPDATED_COURSE)
            .institute(UPDATED_INSTITUTE)
            .speciality(UPDATED_SPECIALITY)
            .course_doc(UPDATED_COURSE_DOC)
            .first_preference(UPDATED_FIRST_PREFERENCE)
            .second_preference(UPDATED_SECOND_PREFERENCE)
            .third_preference(UPDATED_THIRD_PREFERENCE)
            .entryby(UPDATED_ENTRYBY)
            .entry_date(UPDATED_ENTRY_DATE)
            .status(UPDATED_STATUS);

        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplication)))
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getApplication_no()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testApplication.getApplicant_name()).isEqualTo(UPDATED_APPLICANT_NAME);
        assertThat(testApplication.getApplicant_email()).isEqualTo(UPDATED_APPLICANT_EMAIL);
        assertThat(testApplication.getApplicant_mobile()).isEqualTo(UPDATED_APPLICANT_MOBILE);
        assertThat(testApplication.getApplicant_dob()).isEqualTo(UPDATED_APPLICANT_DOB);
        assertThat(testApplication.getApplicant_address()).isEqualTo(UPDATED_APPLICANT_ADDRESS);
        assertThat(testApplication.getCourse()).isEqualTo(UPDATED_COURSE);
        assertThat(testApplication.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testApplication.getSpeciality()).isEqualTo(UPDATED_SPECIALITY);
        assertThat(testApplication.getCourse_doc()).isEqualTo(UPDATED_COURSE_DOC);
        assertThat(testApplication.getFirst_preference()).isEqualTo(UPDATED_FIRST_PREFERENCE);
        assertThat(testApplication.getSecond_preference()).isEqualTo(UPDATED_SECOND_PREFERENCE);
        assertThat(testApplication.getThird_preference()).isEqualTo(UPDATED_THIRD_PREFERENCE);
        assertThat(testApplication.getEntryby()).isEqualTo(UPDATED_ENTRYBY);
        assertThat(testApplication.getEntry_date()).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testApplication.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Create the Application

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteApplication() throws Exception {
        // Initialize the database
        application.setId(UUID.randomUUID());
        applicationRepository.save(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc.perform(delete("/api/applications/{id}", application.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Application.class);
        Application application1 = new Application();
        application1.setId(UUID.randomUUID());
        Application application2 = new Application();
        application2.setId(application1.getId());
        assertThat(application1).isEqualTo(application2);
        application2.setId(UUID.randomUUID());
        assertThat(application1).isNotEqualTo(application2);
        application1.setId(null);
        assertThat(application1).isNotEqualTo(application2);
    }
}
