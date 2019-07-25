package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Institute;
import com.mycompany.myapp.repository.InstituteRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Institute}.
 */
@RestController
@RequestMapping("/api")
public class InstituteResource {

    private final Logger log = LoggerFactory.getLogger(InstituteResource.class);

    private static final String ENTITY_NAME = "institute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstituteRepository instituteRepository;

    public InstituteResource(InstituteRepository instituteRepository) {
        this.instituteRepository = instituteRepository;
    }

    /**
     * {@code POST  /institutes} : Create a new institute.
     *
     * @param institute the institute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new institute, or with status {@code 400 (Bad Request)} if the institute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/institutes")
    public ResponseEntity<Institute> createInstitute(@RequestBody Institute institute) throws URISyntaxException {
        log.debug("REST request to save Institute : {}", institute);
        if (institute.getId() != null) {
            throw new BadRequestAlertException("A new institute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        institute.setId(UUID.randomUUID());
        Institute result = instituteRepository.save(institute);
        return ResponseEntity.created(new URI("/api/institutes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /institutes} : Updates an existing institute.
     *
     * @param institute the institute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institute,
     * or with status {@code 400 (Bad Request)} if the institute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the institute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/institutes")
    public ResponseEntity<Institute> updateInstitute(@RequestBody Institute institute) throws URISyntaxException {
        log.debug("REST request to update Institute : {}", institute);
        if (institute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Institute result = instituteRepository.save(institute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, institute.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /institutes} : get all the institutes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of institutes in body.
     */
    @GetMapping("/institutes")
    public List<Institute> getAllInstitutes() {
        log.debug("REST request to get all Institutes");
        return instituteRepository.findAll();
    }

    /**
     * {@code GET  /institutes/:id} : get the "id" institute.
     *
     * @param id the id of the institute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the institute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/institutes/{id}")
    public ResponseEntity<Institute> getInstitute(@PathVariable UUID id) {
        log.debug("REST request to get Institute : {}", id);
        Optional<Institute> institute = instituteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(institute);
    }

    /**
     * {@code DELETE  /institutes/:id} : delete the "id" institute.
     *
     * @param id the id of the institute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/institutes/{id}")
    public ResponseEntity<Void> deleteInstitute(@PathVariable UUID id) {
        log.debug("REST request to delete Institute : {}", id);
        instituteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
