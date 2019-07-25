package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ApplicationlogDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Applicationlog}.
 */
public interface ApplicationlogService {

    /**
     * Save a applicationlog.
     *
     * @param applicationlogDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationlogDTO save(ApplicationlogDTO applicationlogDTO);

    /**
     * Get all the applicationlogs.
     *
     * @return the list of entities.
     */
    List<ApplicationlogDTO> findAll();


    /**
     * Get the "id" applicationlog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationlogDTO> findOne(UUID id);

    /**
     * Delete the "id" applicationlog.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
