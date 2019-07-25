package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Speciality;
import com.mycompany.myapp.repository.SpecialityRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Speciality}.
 */
@RestController
@RequestMapping("/api")
public class SpecialityResource {

    private final Logger log = LoggerFactory.getLogger(SpecialityResource.class);

    private static final String ENTITY_NAME = "speciality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialityRepository specialityRepository;

    public SpecialityResource(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    /**
     * {@code POST  /specialities} : Create a new speciality.
     *
     * @param speciality the speciality to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speciality, or with status {@code 400 (Bad Request)} if the speciality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specialities")
    public ResponseEntity<Speciality> createSpeciality(@RequestBody Speciality speciality) throws URISyntaxException {
        log.debug("REST request to save Speciality : {}", speciality);
        if (speciality.getId() != null) {
            throw new BadRequestAlertException("A new speciality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        speciality.setId(UUID.randomUUID());
        Speciality result = specialityRepository.save(speciality);
        return ResponseEntity.created(new URI("/api/specialities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specialities} : Updates an existing speciality.
     *
     * @param speciality the speciality to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speciality,
     * or with status {@code 400 (Bad Request)} if the speciality is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speciality couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specialities")
    public ResponseEntity<Speciality> updateSpeciality(@RequestBody Speciality speciality) throws URISyntaxException {
        log.debug("REST request to update Speciality : {}", speciality);
        if (speciality.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Speciality result = specialityRepository.save(speciality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speciality.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specialities} : get all the specialities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialities in body.
     */
    @GetMapping("/specialities")
    public List<Speciality> getAllSpecialities() {
        log.debug("REST request to get all Specialities");
        return specialityRepository.findAll();
    }

    /**
     * {@code GET  /specialities/:id} : get the "id" speciality.
     *
     * @param id the id of the speciality to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speciality, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specialities/{id}")
    public ResponseEntity<Speciality> getSpeciality(@PathVariable UUID id) {
        log.debug("REST request to get Speciality : {}", id);
        Optional<Speciality> speciality = specialityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(speciality);
    }

    /**
     * {@code DELETE  /specialities/:id} : delete the "id" speciality.
     *
     * @param id the id of the speciality to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specialities/{id}")
    public ResponseEntity<Void> deleteSpeciality(@PathVariable UUID id) {
        log.debug("REST request to delete Speciality : {}", id);
        specialityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
