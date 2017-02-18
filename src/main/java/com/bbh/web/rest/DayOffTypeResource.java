package com.bbh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bbh.domain.DayOffType;

import com.bbh.repository.DayOffTypeRepository;
import com.bbh.web.rest.util.HeaderUtil;
import com.bbh.service.dto.DayOffTypeDTO;
import com.bbh.service.mapper.DayOffTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing DayOffType.
 */
@RestController
@RequestMapping("/api")
public class DayOffTypeResource {

    private final Logger log = LoggerFactory.getLogger(DayOffTypeResource.class);
        
    @Inject
    private DayOffTypeRepository dayOffTypeRepository;

    @Inject
    private DayOffTypeMapper dayOffTypeMapper;

    /**
     * POST  /day-off-types : Create a new dayOffType.
     *
     * @param dayOffTypeDTO the dayOffTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dayOffTypeDTO, or with status 400 (Bad Request) if the dayOffType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/day-off-types")
    @Timed
    public ResponseEntity<DayOffTypeDTO> createDayOffType(@RequestBody DayOffTypeDTO dayOffTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DayOffType : {}", dayOffTypeDTO);
        if (dayOffTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dayOffType", "idexists", "A new dayOffType cannot already have an ID")).body(null);
        }
        DayOffType dayOffType = dayOffTypeMapper.dayOffTypeDTOToDayOffType(dayOffTypeDTO);
        dayOffType = dayOffTypeRepository.save(dayOffType);
        DayOffTypeDTO result = dayOffTypeMapper.dayOffTypeToDayOffTypeDTO(dayOffType);
        return ResponseEntity.created(new URI("/api/day-off-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dayOffType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /day-off-types : Updates an existing dayOffType.
     *
     * @param dayOffTypeDTO the dayOffTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dayOffTypeDTO,
     * or with status 400 (Bad Request) if the dayOffTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the dayOffTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/day-off-types")
    @Timed
    public ResponseEntity<DayOffTypeDTO> updateDayOffType(@RequestBody DayOffTypeDTO dayOffTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DayOffType : {}", dayOffTypeDTO);
        if (dayOffTypeDTO.getId() == null) {
            return createDayOffType(dayOffTypeDTO);
        }
        DayOffType dayOffType = dayOffTypeMapper.dayOffTypeDTOToDayOffType(dayOffTypeDTO);
        dayOffType = dayOffTypeRepository.save(dayOffType);
        DayOffTypeDTO result = dayOffTypeMapper.dayOffTypeToDayOffTypeDTO(dayOffType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dayOffType", dayOffTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /day-off-types : get all the dayOffTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dayOffTypes in body
     */
    @GetMapping("/day-off-types")
    @Timed
    public List<DayOffTypeDTO> getAllDayOffTypes() {
        log.debug("REST request to get all DayOffTypes");
        List<DayOffType> dayOffTypes = dayOffTypeRepository.findAll();
        return dayOffTypeMapper.dayOffTypesToDayOffTypeDTOs(dayOffTypes);
    }

    /**
     * GET  /day-off-types/:id : get the "id" dayOffType.
     *
     * @param id the id of the dayOffTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dayOffTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/day-off-types/{id}")
    @Timed
    public ResponseEntity<DayOffTypeDTO> getDayOffType(@PathVariable Long id) {
        log.debug("REST request to get DayOffType : {}", id);
        DayOffType dayOffType = dayOffTypeRepository.findOne(id);
        DayOffTypeDTO dayOffTypeDTO = dayOffTypeMapper.dayOffTypeToDayOffTypeDTO(dayOffType);
        return Optional.ofNullable(dayOffTypeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /day-off-types/:id : delete the "id" dayOffType.
     *
     * @param id the id of the dayOffTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/day-off-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDayOffType(@PathVariable Long id) {
        log.debug("REST request to delete DayOffType : {}", id);
        dayOffTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dayOffType", id.toString())).build();
    }

}
