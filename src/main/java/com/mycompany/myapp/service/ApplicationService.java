package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ApplicationDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Application}.
 */
public interface ApplicationService {

    /**
     * Save a application.
     *
     * @param applicationDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationDTO save(ApplicationDTO applicationDTO);

    /**
     * Get all the applications.
     *
     * @return the list of entities.
     */
    List<ApplicationDTO> findAll();


    /**
     * Get the "id" application.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationDTO> findOne(UUID id);

    /**
     * Delete the "id" application.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
