package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SpecialityDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Speciality}.
 */
public interface SpecialityService {

    /**
     * Save a speciality.
     *
     * @param specialityDTO the entity to save.
     * @return the persisted entity.
     */
    SpecialityDTO save(SpecialityDTO specialityDTO);

    /**
     * Get all the specialities.
     *
     * @return the list of entities.
     */
    List<SpecialityDTO> findAll();


    /**
     * Get the "id" speciality.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpecialityDTO> findOne(UUID id);

    /**
     * Delete the "id" speciality.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
