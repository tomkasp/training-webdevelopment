package com.bbh.web.rest;

import com.bbh.TrainingApp;

import com.bbh.domain.DaysOff;
import com.bbh.repository.DaysOffRepository;
import com.bbh.service.dto.DaysOffDTO;
import com.bbh.service.mapper.DaysOffMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DaysOffResource REST controller.
 *
 * @see DaysOffResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class DaysOffResourceIntTest {

    private static final ZonedDateTime DEFAULT_DAY_OFF_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DAY_OFF_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DAY_OFF_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_DAY_OFF_DATE);

    @Inject
    private DaysOffRepository daysOffRepository;

    @Inject
    private DaysOffMapper daysOffMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDaysOffMockMvc;

    private DaysOff daysOff;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DaysOffResource daysOffResource = new DaysOffResource();
        ReflectionTestUtils.setField(daysOffResource, "daysOffRepository", daysOffRepository);
        ReflectionTestUtils.setField(daysOffResource, "daysOffMapper", daysOffMapper);
        this.restDaysOffMockMvc = MockMvcBuilders.standaloneSetup(daysOffResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DaysOff createEntity(EntityManager em) {
        DaysOff daysOff = new DaysOff()
                .dayOffDate(DEFAULT_DAY_OFF_DATE);
        return daysOff;
    }

    @Before
    public void initTest() {
        daysOff = createEntity(em);
    }

    @Test
    @Transactional
    public void createDaysOff() throws Exception {
        int databaseSizeBeforeCreate = daysOffRepository.findAll().size();

        // Create the DaysOff
        DaysOffDTO daysOffDTO = daysOffMapper.daysOffToDaysOffDTO(daysOff);

        restDaysOffMockMvc.perform(post("/api/days-offs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(daysOffDTO)))
                .andExpect(status().isCreated());

        // Validate the DaysOff in the database
        List<DaysOff> daysOffs = daysOffRepository.findAll();
        assertThat(daysOffs).hasSize(databaseSizeBeforeCreate + 1);
        DaysOff testDaysOff = daysOffs.get(daysOffs.size() - 1);
        assertThat(testDaysOff.getDayOffDate()).isEqualTo(DEFAULT_DAY_OFF_DATE);
    }

    @Test
    @Transactional
    public void getAllDaysOffs() throws Exception {
        // Initialize the database
        daysOffRepository.saveAndFlush(daysOff);

        // Get all the daysOffs
        restDaysOffMockMvc.perform(get("/api/days-offs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(daysOff.getId().intValue())))
                .andExpect(jsonPath("$.[*].dayOffDate").value(hasItem(DEFAULT_DAY_OFF_DATE_STR)));
    }

    @Test
    @Transactional
    public void getDaysOff() throws Exception {
        // Initialize the database
        daysOffRepository.saveAndFlush(daysOff);

        // Get the daysOff
        restDaysOffMockMvc.perform(get("/api/days-offs/{id}", daysOff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(daysOff.getId().intValue()))
            .andExpect(jsonPath("$.dayOffDate").value(DEFAULT_DAY_OFF_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingDaysOff() throws Exception {
        // Get the daysOff
        restDaysOffMockMvc.perform(get("/api/days-offs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDaysOff() throws Exception {
        // Initialize the database
        daysOffRepository.saveAndFlush(daysOff);
        int databaseSizeBeforeUpdate = daysOffRepository.findAll().size();

        // Update the daysOff
        DaysOff updatedDaysOff = daysOffRepository.findOne(daysOff.getId());
        updatedDaysOff
                .dayOffDate(UPDATED_DAY_OFF_DATE);
        DaysOffDTO daysOffDTO = daysOffMapper.daysOffToDaysOffDTO(updatedDaysOff);

        restDaysOffMockMvc.perform(put("/api/days-offs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(daysOffDTO)))
                .andExpect(status().isOk());

        // Validate the DaysOff in the database
        List<DaysOff> daysOffs = daysOffRepository.findAll();
        assertThat(daysOffs).hasSize(databaseSizeBeforeUpdate);
        DaysOff testDaysOff = daysOffs.get(daysOffs.size() - 1);
        assertThat(testDaysOff.getDayOffDate()).isEqualTo(UPDATED_DAY_OFF_DATE);
    }

    @Test
    @Transactional
    public void deleteDaysOff() throws Exception {
        // Initialize the database
        daysOffRepository.saveAndFlush(daysOff);
        int databaseSizeBeforeDelete = daysOffRepository.findAll().size();

        // Get the daysOff
        restDaysOffMockMvc.perform(delete("/api/days-offs/{id}", daysOff.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DaysOff> daysOffs = daysOffRepository.findAll();
        assertThat(daysOffs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
