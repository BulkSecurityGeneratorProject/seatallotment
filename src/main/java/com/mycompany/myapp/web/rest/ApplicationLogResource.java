package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ApplicationLog;
import com.mycompany.myapp.repository.ApplicationLogRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ApplicationLog}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationLogResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationLogResource.class);

    private static final String ENTITY_NAME = "applicationLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationLogRepository applicationLogRepository;

    public ApplicationLogResource(ApplicationLogRepository applicationLogRepository) {
        this.applicationLogRepository = applicationLogRepository;
    }

    /**
     * {@code POST  /application-logs} : Create a new applicationLog.
     *
     * @param applicationLog the applicationLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationLog, or with status {@code 400 (Bad Request)} if the applicationLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-logs")
    public ResponseEntity<ApplicationLog> createApplicationLog(@RequestBody ApplicationLog applicationLog) throws URISyntaxException {
        log.debug("REST request to save ApplicationLog : {}", applicationLog);
        if (applicationLog.getId() != null) {
            throw new BadRequestAlertException("A new applicationLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        applicationLog.setId(UUID.randomUUID());
        ApplicationLog result = applicationLogRepository.save(applicationLog);
        return ResponseEntity.created(new URI("/api/application-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-logs} : Updates an existing applicationLog.
     *
     * @param applicationLog the applicationLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationLog,
     * or with status {@code 400 (Bad Request)} if the applicationLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-logs")
    public ResponseEntity<ApplicationLog> updateApplicationLog(@RequestBody ApplicationLog applicationLog) throws URISyntaxException {
        log.debug("REST request to update ApplicationLog : {}", applicationLog);
        if (applicationLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationLog result = applicationLogRepository.save(applicationLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-logs} : get all the applicationLogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationLogs in body.
     */
    @GetMapping("/application-logs")
    public List<ApplicationLog> getAllApplicationLogs() {
        log.debug("REST request to get all ApplicationLogs");
        return applicationLogRepository.findAll();
    }

    /**
     * {@code GET  /application-logs/:id} : get the "id" applicationLog.
     *
     * @param id the id of the applicationLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-logs/{id}")
    public ResponseEntity<ApplicationLog> getApplicationLog(@PathVariable UUID id) {
        log.debug("REST request to get ApplicationLog : {}", id);
        Optional<ApplicationLog> applicationLog = applicationLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applicationLog);
    }

    /**
     * {@code DELETE  /application-logs/:id} : delete the "id" applicationLog.
     *
     * @param id the id of the applicationLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-logs/{id}")
    public ResponseEntity<Void> deleteApplicationLog(@PathVariable UUID id) {
        log.debug("REST request to delete ApplicationLog : {}", id);
        applicationLogRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
