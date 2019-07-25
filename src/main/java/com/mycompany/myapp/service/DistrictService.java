package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DistrictDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.District}.
 */
public interface DistrictService {

    /**
     * Save a district.
     *
     * @param districtDTO the entity to save.
     * @return the persisted entity.
     */
    DistrictDTO save(DistrictDTO districtDTO);

    /**
     * Get all the districts.
     *
     * @return the list of entities.
     */
    List<DistrictDTO> findAll();


    /**
     * Get the "id" district.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictDTO> findOne(UUID id);

    /**
     * Delete the "id" district.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
