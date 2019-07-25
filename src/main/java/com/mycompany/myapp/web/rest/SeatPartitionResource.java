package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.SeatPartitionService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.SeatPartitionDTO;

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

    private final SeatPartitionService seatPartitionService;

    public SeatPartitionResource(SeatPartitionService seatPartitionService) {
        this.seatPartitionService = seatPartitionService;
    }

    /**
     * {@code POST  /seat-partitions} : Create a new seatPartition.
     *
     * @param seatPartitionDTO the seatPartitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seatPartitionDTO, or with status {@code 400 (Bad Request)} if the seatPartition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seat-partitions")
    public ResponseEntity<SeatPartitionDTO> createSeatPartition(@RequestBody SeatPartitionDTO seatPartitionDTO) throws URISyntaxException {
        log.debug("REST request to save SeatPartition : {}", seatPartitionDTO);
        if (seatPartitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new seatPartition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        seatPartitionDTO.setId(UUID.randomUUID());
        SeatPartitionDTO result = seatPartitionService.save(seatPartitionDTO);
        return ResponseEntity.created(new URI("/api/seat-partitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seat-partitions} : Updates an existing seatPartition.
     *
     * @param seatPartitionDTO the seatPartitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seatPartitionDTO,
     * or with status {@code 400 (Bad Request)} if the seatPartitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seatPartitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seat-partitions")
    public ResponseEntity<SeatPartitionDTO> updateSeatPartition(@RequestBody SeatPartitionDTO seatPartitionDTO) throws URISyntaxException {
        log.debug("REST request to update SeatPartition : {}", seatPartitionDTO);
        if (seatPartitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeatPartitionDTO result = seatPartitionService.save(seatPartitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seatPartitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /seat-partitions} : get all the seatPartitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seatPartitions in body.
     */
    @GetMapping("/seat-partitions")
    public List<SeatPartitionDTO> getAllSeatPartitions() {
        log.debug("REST request to get all SeatPartitions");
        return seatPartitionService.findAll();
    }

    /**
     * {@code GET  /seat-partitions/:id} : get the "id" seatPartition.
     *
     * @param id the id of the seatPartitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seatPartitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seat-partitions/{id}")
    public ResponseEntity<SeatPartitionDTO> getSeatPartition(@PathVariable UUID id) {
        log.debug("REST request to get SeatPartition : {}", id);
        Optional<SeatPartitionDTO> seatPartitionDTO = seatPartitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seatPartitionDTO);
    }

    /**
     * {@code DELETE  /seat-partitions/:id} : delete the "id" seatPartition.
     *
     * @param id the id of the seatPartitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seat-partitions/{id}")
    public ResponseEntity<Void> deleteSeatPartition(@PathVariable UUID id) {
        log.debug("REST request to delete SeatPartition : {}", id);
        seatPartitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
