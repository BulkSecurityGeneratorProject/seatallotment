package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.SeatAllotmentApp;
import com.mycompany.myapp.domain.SeatPartition;
import com.mycompany.myapp.repository.SeatPartitionRepository;
import com.mycompany.myapp.service.SeatPartitionService;
import com.mycompany.myapp.service.dto.SeatPartitionDTO;
import com.mycompany.myapp.service.mapper.SeatPartitionMapper;
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
 * Integration tests for the {@Link SeatPartitionResource} REST controller.
 */
@SpringBootTest(classes = SeatAllotmentApp.class)
public class SeatPartitionResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final Integer DEFAULT_VACANT = 1;
    private static final Integer UPDATED_VACANT = 2;

    private static final Integer DEFAULT_OCCUPIED = 1;
    private static final Integer UPDATED_OCCUPIED = 2;

    @Autowired
    private SeatPartitionRepository seatPartitionRepository;

    @Autowired
    private SeatPartitionMapper seatPartitionMapper;

    @Autowired
    private SeatPartitionService seatPartitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSeatPartitionMockMvc;

    private SeatPartition seatPartition;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeatPartitionResource seatPartitionResource = new SeatPartitionResource(seatPartitionService);
        this.restSeatPartitionMockMvc = MockMvcBuilders.standaloneSetup(seatPartitionResource)
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
    public static SeatPartition createEntity() {
        SeatPartition seatPartition = new SeatPartition()
            .district(DEFAULT_DISTRICT)
            .total(DEFAULT_TOTAL)
            .vacant(DEFAULT_VACANT)
            .occupied(DEFAULT_OCCUPIED);
        return seatPartition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeatPartition createUpdatedEntity() {
        SeatPartition seatPartition = new SeatPartition()
            .district(UPDATED_DISTRICT)
            .total(UPDATED_TOTAL)
            .vacant(UPDATED_VACANT)
            .occupied(UPDATED_OCCUPIED);
        return seatPartition;
    }

    @BeforeEach
    public void initTest() {
        seatPartitionRepository.deleteAll();
        seatPartition = createEntity();
    }

    @Test
    public void createSeatPartition() throws Exception {
        int databaseSizeBeforeCreate = seatPartitionRepository.findAll().size();

        // Create the SeatPartition
        SeatPartitionDTO seatPartitionDTO = seatPartitionMapper.toDto(seatPartition);
        restSeatPartitionMockMvc.perform(post("/api/seat-partitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatPartitionDTO)))
            .andExpect(status().isCreated());

        // Validate the SeatPartition in the database
        List<SeatPartition> seatPartitionList = seatPartitionRepository.findAll();
        assertThat(seatPartitionList).hasSize(databaseSizeBeforeCreate + 1);
        SeatPartition testSeatPartition = seatPartitionList.get(seatPartitionList.size() - 1);
        assertThat(testSeatPartition.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testSeatPartition.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testSeatPartition.getVacant()).isEqualTo(DEFAULT_VACANT);
        assertThat(testSeatPartition.getOccupied()).isEqualTo(DEFAULT_OCCUPIED);
    }

    @Test
    public void createSeatPartitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seatPartitionRepository.findAll().size();

        // Create the SeatPartition with an existing ID
        seatPartition.setId(UUID.randomUUID());
        SeatPartitionDTO seatPartitionDTO = seatPartitionMapper.toDto(seatPartition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeatPartitionMockMvc.perform(post("/api/seat-partitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatPartitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeatPartition in the database
        List<SeatPartition> seatPartitionList = seatPartitionRepository.findAll();
        assertThat(seatPartitionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllSeatPartitions() throws Exception {
        // Initialize the database
        seatPartition.setId(UUID.randomUUID());
        seatPartitionRepository.save(seatPartition);

        // Get all the seatPartitionList
        restSeatPartitionMockMvc.perform(get("/api/seat-partitions"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seatPartition.getId().toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].vacant").value(hasItem(DEFAULT_VACANT)))
            .andExpect(jsonPath("$.[*].occupied").value(hasItem(DEFAULT_OCCUPIED)));
    }
    
    @Test
    public void getSeatPartition() throws Exception {
        // Initialize the database
        seatPartition.setId(UUID.randomUUID());
        seatPartitionRepository.save(seatPartition);

        // Get the seatPartition
        restSeatPartitionMockMvc.perform(get("/api/seat-partitions/{id}", seatPartition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seatPartition.getId().toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.vacant").value(DEFAULT_VACANT))
            .andExpect(jsonPath("$.occupied").value(DEFAULT_OCCUPIED));
    }

    @Test
    public void getNonExistingSeatPartition() throws Exception {
        // Get the seatPartition
        restSeatPartitionMockMvc.perform(get("/api/seat-partitions/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSeatPartition() throws Exception {
        // Initialize the database
        seatPartition.setId(UUID.randomUUID());
        seatPartitionRepository.save(seatPartition);

        int databaseSizeBeforeUpdate = seatPartitionRepository.findAll().size();

        // Update the seatPartition
        SeatPartition updatedSeatPartition = seatPartitionRepository.findById(seatPartition.getId()).get();
        updatedSeatPartition
            .district(UPDATED_DISTRICT)
            .total(UPDATED_TOTAL)
            .vacant(UPDATED_VACANT)
            .occupied(UPDATED_OCCUPIED);
        SeatPartitionDTO seatPartitionDTO = seatPartitionMapper.toDto(updatedSeatPartition);

        restSeatPartitionMockMvc.perform(put("/api/seat-partitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatPartitionDTO)))
            .andExpect(status().isOk());

        // Validate the SeatPartition in the database
        List<SeatPartition> seatPartitionList = seatPartitionRepository.findAll();
        assertThat(seatPartitionList).hasSize(databaseSizeBeforeUpdate);
        SeatPartition testSeatPartition = seatPartitionList.get(seatPartitionList.size() - 1);
        assertThat(testSeatPartition.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testSeatPartition.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testSeatPartition.getVacant()).isEqualTo(UPDATED_VACANT);
        assertThat(testSeatPartition.getOccupied()).isEqualTo(UPDATED_OCCUPIED);
    }

    @Test
    public void updateNonExistingSeatPartition() throws Exception {
        int databaseSizeBeforeUpdate = seatPartitionRepository.findAll().size();

        // Create the SeatPartition
        SeatPartitionDTO seatPartitionDTO = seatPartitionMapper.toDto(seatPartition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeatPartitionMockMvc.perform(put("/api/seat-partitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatPartitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeatPartition in the database
        List<SeatPartition> seatPartitionList = seatPartitionRepository.findAll();
        assertThat(seatPartitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSeatPartition() throws Exception {
        // Initialize the database
        seatPartition.setId(UUID.randomUUID());
        seatPartitionRepository.save(seatPartition);

        int databaseSizeBeforeDelete = seatPartitionRepository.findAll().size();

        // Delete the seatPartition
        restSeatPartitionMockMvc.perform(delete("/api/seat-partitions/{id}", seatPartition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeatPartition> seatPartitionList = seatPartitionRepository.findAll();
        assertThat(seatPartitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeatPartition.class);
        SeatPartition seatPartition1 = new SeatPartition();
        seatPartition1.setId(UUID.randomUUID());
        SeatPartition seatPartition2 = new SeatPartition();
        seatPartition2.setId(seatPartition1.getId());
        assertThat(seatPartition1).isEqualTo(seatPartition2);
        seatPartition2.setId(UUID.randomUUID());
        assertThat(seatPartition1).isNotEqualTo(seatPartition2);
        seatPartition1.setId(null);
        assertThat(seatPartition1).isNotEqualTo(seatPartition2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeatPartitionDTO.class);
        SeatPartitionDTO seatPartitionDTO1 = new SeatPartitionDTO();
        seatPartitionDTO1.setId(UUID.randomUUID());
        SeatPartitionDTO seatPartitionDTO2 = new SeatPartitionDTO();
        assertThat(seatPartitionDTO1).isNotEqualTo(seatPartitionDTO2);
        seatPartitionDTO2.setId(seatPartitionDTO1.getId());
        assertThat(seatPartitionDTO1).isEqualTo(seatPartitionDTO2);
        seatPartitionDTO2.setId(UUID.randomUUID());
        assertThat(seatPartitionDTO1).isNotEqualTo(seatPartitionDTO2);
        seatPartitionDTO1.setId(null);
        assertThat(seatPartitionDTO1).isNotEqualTo(seatPartitionDTO2);
    }
}
