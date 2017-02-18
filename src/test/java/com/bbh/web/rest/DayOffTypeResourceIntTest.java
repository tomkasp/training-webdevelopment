package com.bbh.web.rest;

import com.bbh.TrainingApp;

import com.bbh.domain.DayOffType;
import com.bbh.repository.DayOffTypeRepository;
import com.bbh.service.dto.DayOffTypeDTO;
import com.bbh.service.mapper.DayOffTypeMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DayOffTypeResource REST controller.
 *
 * @see DayOffTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class DayOffTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private DayOffTypeRepository dayOffTypeRepository;

    @Inject
    private DayOffTypeMapper dayOffTypeMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDayOffTypeMockMvc;

    private DayOffType dayOffType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DayOffTypeResource dayOffTypeResource = new DayOffTypeResource();
        ReflectionTestUtils.setField(dayOffTypeResource, "dayOffTypeRepository", dayOffTypeRepository);
        ReflectionTestUtils.setField(dayOffTypeResource, "dayOffTypeMapper", dayOffTypeMapper);
        this.restDayOffTypeMockMvc = MockMvcBuilders.standaloneSetup(dayOffTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DayOffType createEntity(EntityManager em) {
        DayOffType dayOffType = new DayOffType()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return dayOffType;
    }

    @Before
    public void initTest() {
        dayOffType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDayOffType() throws Exception {
        int databaseSizeBeforeCreate = dayOffTypeRepository.findAll().size();

        // Create the DayOffType
        DayOffTypeDTO dayOffTypeDTO = dayOffTypeMapper.dayOffTypeToDayOffTypeDTO(dayOffType);

        restDayOffTypeMockMvc.perform(post("/api/day-off-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dayOffTypeDTO)))
                .andExpect(status().isCreated());

        // Validate the DayOffType in the database
        List<DayOffType> dayOffTypes = dayOffTypeRepository.findAll();
        assertThat(dayOffTypes).hasSize(databaseSizeBeforeCreate + 1);
        DayOffType testDayOffType = dayOffTypes.get(dayOffTypes.size() - 1);
        assertThat(testDayOffType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDayOffType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDayOffTypes() throws Exception {
        // Initialize the database
        dayOffTypeRepository.saveAndFlush(dayOffType);

        // Get all the dayOffTypes
        restDayOffTypeMockMvc.perform(get("/api/day-off-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dayOffType.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDayOffType() throws Exception {
        // Initialize the database
        dayOffTypeRepository.saveAndFlush(dayOffType);

        // Get the dayOffType
        restDayOffTypeMockMvc.perform(get("/api/day-off-types/{id}", dayOffType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dayOffType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDayOffType() throws Exception {
        // Get the dayOffType
        restDayOffTypeMockMvc.perform(get("/api/day-off-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDayOffType() throws Exception {
        // Initialize the database
        dayOffTypeRepository.saveAndFlush(dayOffType);
        int databaseSizeBeforeUpdate = dayOffTypeRepository.findAll().size();

        // Update the dayOffType
        DayOffType updatedDayOffType = dayOffTypeRepository.findOne(dayOffType.getId());
        updatedDayOffType
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        DayOffTypeDTO dayOffTypeDTO = dayOffTypeMapper.dayOffTypeToDayOffTypeDTO(updatedDayOffType);

        restDayOffTypeMockMvc.perform(put("/api/day-off-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dayOffTypeDTO)))
                .andExpect(status().isOk());

        // Validate the DayOffType in the database
        List<DayOffType> dayOffTypes = dayOffTypeRepository.findAll();
        assertThat(dayOffTypes).hasSize(databaseSizeBeforeUpdate);
        DayOffType testDayOffType = dayOffTypes.get(dayOffTypes.size() - 1);
        assertThat(testDayOffType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDayOffType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteDayOffType() throws Exception {
        // Initialize the database
        dayOffTypeRepository.saveAndFlush(dayOffType);
        int databaseSizeBeforeDelete = dayOffTypeRepository.findAll().size();

        // Get the dayOffType
        restDayOffTypeMockMvc.perform(delete("/api/day-off-types/{id}", dayOffType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DayOffType> dayOffTypes = dayOffTypeRepository.findAll();
        assertThat(dayOffTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
