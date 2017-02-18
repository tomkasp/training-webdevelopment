package com.bbh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bbh.domain.DaysOff;

import com.bbh.repository.DaysOffRepository;
import com.bbh.web.rest.util.HeaderUtil;
import com.bbh.service.dto.DaysOffDTO;
import com.bbh.service.mapper.DaysOffMapper;
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
 * REST controller for managing DaysOff.
 */
@RestController
@RequestMapping("/api")
public class DaysOffResource {

    private final Logger log = LoggerFactory.getLogger(DaysOffResource.class);
        
    @Inject
    private DaysOffRepository daysOffRepository;

    @Inject
    private DaysOffMapper daysOffMapper;

    /**
     * POST  /days-offs : Create a new daysOff.
     *
     * @param daysOffDTO the daysOffDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new daysOffDTO, or with status 400 (Bad Request) if the daysOff has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/days-offs")
    @Timed
    public ResponseEntity<DaysOffDTO> createDaysOff(@RequestBody DaysOffDTO daysOffDTO) throws URISyntaxException {
        log.debug("REST request to save DaysOff : {}", daysOffDTO);
        if (daysOffDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("daysOff", "idexists", "A new daysOff cannot already have an ID")).body(null);
        }
        DaysOff daysOff = daysOffMapper.daysOffDTOToDaysOff(daysOffDTO);
        daysOff = daysOffRepository.save(daysOff);
        DaysOffDTO result = daysOffMapper.daysOffToDaysOffDTO(daysOff);
        return ResponseEntity.created(new URI("/api/days-offs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("daysOff", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /days-offs : Updates an existing daysOff.
     *
     * @param daysOffDTO the daysOffDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated daysOffDTO,
     * or with status 400 (Bad Request) if the daysOffDTO is not valid,
     * or with status 500 (Internal Server Error) if the daysOffDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/days-offs")
    @Timed
    public ResponseEntity<DaysOffDTO> updateDaysOff(@RequestBody DaysOffDTO daysOffDTO) throws URISyntaxException {
        log.debug("REST request to update DaysOff : {}", daysOffDTO);
        if (daysOffDTO.getId() == null) {
            return createDaysOff(daysOffDTO);
        }
        DaysOff daysOff = daysOffMapper.daysOffDTOToDaysOff(daysOffDTO);
        daysOff = daysOffRepository.save(daysOff);
        DaysOffDTO result = daysOffMapper.daysOffToDaysOffDTO(daysOff);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("daysOff", daysOffDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /days-offs : get all the daysOffs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of daysOffs in body
     */
    @GetMapping("/days-offs")
    @Timed
    public List<DaysOffDTO> getAllDaysOffs() {
        log.debug("REST request to get all DaysOffs");
        List<DaysOff> daysOffs = daysOffRepository.findAll();
        return daysOffMapper.daysOffsToDaysOffDTOs(daysOffs);
    }

    /**
     * GET  /days-offs/:id : get the "id" daysOff.
     *
     * @param id the id of the daysOffDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the daysOffDTO, or with status 404 (Not Found)
     */
    @GetMapping("/days-offs/{id}")
    @Timed
    public ResponseEntity<DaysOffDTO> getDaysOff(@PathVariable Long id) {
        log.debug("REST request to get DaysOff : {}", id);
        DaysOff daysOff = daysOffRepository.findOne(id);
        DaysOffDTO daysOffDTO = daysOffMapper.daysOffToDaysOffDTO(daysOff);
        return Optional.ofNullable(daysOffDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /days-offs/:id : delete the "id" daysOff.
     *
     * @param id the id of the daysOffDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/days-offs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDaysOff(@PathVariable Long id) {
        log.debug("REST request to delete DaysOff : {}", id);
        daysOffRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("daysOff", id.toString())).build();
    }

}
