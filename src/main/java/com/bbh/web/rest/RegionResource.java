package com.bbh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bbh.domain.Region;

import com.bbh.repository.RegionRepository;
import com.bbh.web.rest.util.HeaderUtil;
import com.bbh.service.dto.RegionDTO;
import com.bbh.service.mapper.RegionMapper;
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
 * REST controller for managing Region.
 */
@RestController
@RequestMapping("/api")
public class RegionResource {

    private final Logger log = LoggerFactory.getLogger(RegionResource.class);
        
    @Inject
    private RegionRepository regionRepository;

    @Inject
    private RegionMapper regionMapper;

    /**
     * POST  /regions : Create a new region.
     *
     * @param regionDTO the regionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regionDTO, or with status 400 (Bad Request) if the region has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regions")
    @Timed
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO regionDTO) throws URISyntaxException {
        log.debug("REST request to save Region : {}", regionDTO);
        if (regionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("region", "idexists", "A new region cannot already have an ID")).body(null);
        }
        Region region = regionMapper.regionDTOToRegion(regionDTO);
        region = regionRepository.save(region);
        RegionDTO result = regionMapper.regionToRegionDTO(region);
        return ResponseEntity.created(new URI("/api/regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("region", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regions : Updates an existing region.
     *
     * @param regionDTO the regionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regionDTO,
     * or with status 400 (Bad Request) if the regionDTO is not valid,
     * or with status 500 (Internal Server Error) if the regionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regions")
    @Timed
    public ResponseEntity<RegionDTO> updateRegion(@RequestBody RegionDTO regionDTO) throws URISyntaxException {
        log.debug("REST request to update Region : {}", regionDTO);
        if (regionDTO.getId() == null) {
            return createRegion(regionDTO);
        }
        Region region = regionMapper.regionDTOToRegion(regionDTO);
        region = regionRepository.save(region);
        RegionDTO result = regionMapper.regionToRegionDTO(region);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("region", regionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regions : get all the regions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of regions in body
     */
    @GetMapping("/regions")
    @Timed
    public List<RegionDTO> getAllRegions() {
        log.debug("REST request to get all Regions");
        List<Region> regions = regionRepository.findAll();
        return regionMapper.regionsToRegionDTOs(regions);
    }

    /**
     * GET  /regions/:id : get the "id" region.
     *
     * @param id the id of the regionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/regions/{id}")
    @Timed
    public ResponseEntity<RegionDTO> getRegion(@PathVariable Long id) {
        log.debug("REST request to get Region : {}", id);
        Region region = regionRepository.findOne(id);
        RegionDTO regionDTO = regionMapper.regionToRegionDTO(region);
        return Optional.ofNullable(regionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /regions/:id : delete the "id" region.
     *
     * @param id the id of the regionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        log.debug("REST request to delete Region : {}", id);
        regionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("region", id.toString())).build();
    }

}
