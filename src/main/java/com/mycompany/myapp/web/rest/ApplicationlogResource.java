package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ApplicationlogService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ApplicationlogDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Applicationlog}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationlogResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationlogResource.class);

    private static final String ENTITY_NAME = "applicationlog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationlogService applicationlogService;

    public ApplicationlogResource(ApplicationlogService applicationlogService) {
        this.applicationlogService = applicationlogService;
    }

    /**
     * {@code POST  /applicationlogs} : Create a new applicationlog.
     *
     * @param applicationlogDTO the applicationlogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationlogDTO, or with status {@code 400 (Bad Request)} if the applicationlog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicationlogs")
    public ResponseEntity<ApplicationlogDTO> createApplicationlog(@RequestBody ApplicationlogDTO applicationlogDTO) throws URISyntaxException {
        log.debug("REST request to save Applicationlog : {}", applicationlogDTO);
        if (applicationlogDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationlog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        applicationlogDTO.setId(UUID.randomUUID());
        ApplicationlogDTO result = applicationlogService.save(applicationlogDTO);
        return ResponseEntity.created(new URI("/api/applicationlogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicationlogs} : Updates an existing applicationlog.
     *
     * @param applicationlogDTO the applicationlogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationlogDTO,
     * or with status {@code 400 (Bad Request)} if the applicationlogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationlogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicationlogs")
    public ResponseEntity<ApplicationlogDTO> updateApplicationlog(@RequestBody ApplicationlogDTO applicationlogDTO) throws URISyntaxException {
        log.debug("REST request to update Applicationlog : {}", applicationlogDTO);
        if (applicationlogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationlogDTO result = applicationlogService.save(applicationlogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationlogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applicationlogs} : get all the applicationlogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationlogs in body.
     */
    @GetMapping("/applicationlogs")
    public List<ApplicationlogDTO> getAllApplicationlogs() {
        log.debug("REST request to get all Applicationlogs");
        return applicationlogService.findAll();
    }

    /**
     * {@code GET  /applicationlogs/:id} : get the "id" applicationlog.
     *
     * @param id the id of the applicationlogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationlogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicationlogs/{id}")
    public ResponseEntity<ApplicationlogDTO> getApplicationlog(@PathVariable UUID id) {
        log.debug("REST request to get Applicationlog : {}", id);
        Optional<ApplicationlogDTO> applicationlogDTO = applicationlogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationlogDTO);
    }

    /**
     * {@code DELETE  /applicationlogs/:id} : delete the "id" applicationlog.
     *
     * @param id the id of the applicationlogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicationlogs/{id}")
    public ResponseEntity<Void> deleteApplicationlog(@PathVariable UUID id) {
        log.debug("REST request to delete Applicationlog : {}", id);
        applicationlogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
