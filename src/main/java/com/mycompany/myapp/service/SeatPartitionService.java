package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SeatPartitionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.SeatPartition}.
 */
public interface SeatPartitionService {

    /**
     * Save a seatPartition.
     *
     * @param seatPartitionDTO the entity to save.
     * @return the persisted entity.
     */
    SeatPartitionDTO save(SeatPartitionDTO seatPartitionDTO);

    /**
     * Get all the seatPartitions.
     *
     * @return the list of entities.
     */
    List<SeatPartitionDTO> findAll();


    /**
     * Get the "id" seatPartition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeatPartitionDTO> findOne(UUID id);

    /**
     * Delete the "id" seatPartition.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
