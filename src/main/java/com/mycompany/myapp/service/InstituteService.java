package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.InstituteDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Institute}.
 */
public interface InstituteService {

    /**
     * Save a institute.
     *
     * @param instituteDTO the entity to save.
     * @return the persisted entity.
     */
    InstituteDTO save(InstituteDTO instituteDTO);

    /**
     * Get all the institutes.
     *
     * @return the list of entities.
     */
    List<InstituteDTO> findAll();


    /**
     * Get the "id" institute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstituteDTO> findOne(UUID id);

    /**
     * Delete the "id" institute.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
