package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.District;
import com.mycompany.myapp.repository.DistrictRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.District}.
 */
@RestController
@RequestMapping("/api")
public class DistrictResource {

    private final Logger log = LoggerFactory.getLogger(DistrictResource.class);

    private static final String ENTITY_NAME = "district";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictRepository districtRepository;

    public DistrictResource(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    /**
     * {@code POST  /districts} : Create a new district.
     *
     * @param district the district to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new district, or with status {@code 400 (Bad Request)} if the district has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/districts")
    public ResponseEntity<District> createDistrict(@RequestBody District district) throws URISyntaxException {
        log.debug("REST request to save District : {}", district);
        if (district.getId() != null) {
            throw new BadRequestAlertException("A new district cannot already have an ID", ENTITY_NAME, "idexists");
        }
        district.setId(UUID.randomUUID());
        District result = districtRepository.save(district);
        return ResponseEntity.created(new URI("/api/districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /districts} : Updates an existing district.
     *
     * @param district the district to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated district,
     * or with status {@code 400 (Bad Request)} if the district is not valid,
     * or with status {@code 500 (Internal Server Error)} if the district couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/districts")
    public ResponseEntity<District> updateDistrict(@RequestBody District district) throws URISyntaxException {
        log.debug("REST request to update District : {}", district);
        if (district.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        District result = districtRepository.save(district);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, district.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /districts} : get all the districts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districts in body.
     */
    @GetMapping("/districts")
    public List<District> getAllDistricts() {
        log.debug("REST request to get all Districts");
        return districtRepository.findAll();
    }

    /**
     * {@code GET  /districts/:id} : get the "id" district.
     *
     * @param id the id of the district to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the district, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/districts/{id}")
    public ResponseEntity<District> getDistrict(@PathVariable UUID id) {
        log.debug("REST request to get District : {}", id);
        Optional<District> district = districtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(district);
    }

    /**
     * {@code DELETE  /districts/:id} : delete the "id" district.
     *
     * @param id the id of the district to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable UUID id) {
        log.debug("REST request to delete District : {}", id);
        districtRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
