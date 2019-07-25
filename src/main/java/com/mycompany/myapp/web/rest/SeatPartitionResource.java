package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SeatPartition;
import com.mycompany.myapp.repository.SeatPartitionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SeatPartition}.
 */
@RestController
@RequestMapping("/api")
public class SeatPartitionResource {

    private final Logger log = LoggerFactory.getLogger(SeatPartitionResource.class);

    private static final String ENTITY_NAME = "seatPartition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeatPartitionRepository seatPartitionRepository;

    public SeatPartitionResource(SeatPartitionRepository seatPartitionRepository) {
        this.seatPartitionRepository = seatPartitionRepository;
    }

    /**
     * {@code POST  /seat-partitions} : Create a new seatPartition.
     *
     * @param seatPartition the seatPartition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seatPartition, or with status {@code 400 (Bad Request)} if the seatPartition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seat-partitions")
    public ResponseEntity<SeatPartition> createSeatPartition(@RequestBody SeatPartition seatPartition) throws URISyntaxException {
        log.debug("REST request to save SeatPartition : {}", seatPartition);
        if (seatPartition.getId() != null) {
            throw new BadRequestAlertException("A new seatPartition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        seatPartition.setId(UUID.randomUUID());
        SeatPartition result = seatPartitionRepository.save(seatPartition);
        return ResponseEntity.created(new URI("/api/seat-partitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seat-partitions} : Updates an existing seatPartition.
     *
     * @param seatPartition the seatPartition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seatPartition,
     * or with status {@code 400 (Bad Request)} if the seatPartition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seatPartition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seat-partitions")
    public ResponseEntity<SeatPartition> updateSeatPartition(@RequestBody SeatPartition seatPartition) throws URISyntaxException {
        log.debug("REST request to update SeatPartition : {}", seatPartition);
        if (seatPartition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeatPartition result = seatPartitionRepository.save(seatPartition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seatPartition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /seat-partitions} : get all the seatPartitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seatPartitions in body.
     */
    @GetMapping("/seat-partitions")
    public List<SeatPartition> getAllSeatPartitions() {
        log.debug("REST request to get all SeatPartitions");
        return seatPartitionRepository.findAll();
    }

    /**
     * {@code GET  /seat-partitions/:id} : get the "id" seatPartition.
     *
     * @param id the id of the seatPartition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seatPartition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seat-partitions/{id}")
    public ResponseEntity<SeatPartition> getSeatPartition(@PathVariable UUID id) {
        log.debug("REST request to get SeatPartition : {}", id);
        Optional<SeatPartition> seatPartition = seatPartitionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(seatPartition);
    }

    /**
     * {@code DELETE  /seat-partitions/:id} : delete the "id" seatPartition.
     *
     * @param id the id of the seatPartition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seat-partitions/{id}")
    public ResponseEntity<Void> deleteSeatPartition(@PathVariable UUID id) {
        log.debug("REST request to delete SeatPartition : {}", id);
        seatPartitionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
