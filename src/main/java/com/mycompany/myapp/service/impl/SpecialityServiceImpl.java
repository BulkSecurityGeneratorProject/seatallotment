package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SpecialityService;
import com.mycompany.myapp.domain.Speciality;
import com.mycompany.myapp.repository.SpecialityRepository;
import com.mycompany.myapp.service.dto.SpecialityDTO;
import com.mycompany.myapp.service.mapper.SpecialityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Speciality}.
 */
@Service
public class SpecialityServiceImpl implements SpecialityService {

    private final Logger log = LoggerFactory.getLogger(SpecialityServiceImpl.class);

    private final SpecialityRepository specialityRepository;

    private final SpecialityMapper specialityMapper;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository, SpecialityMapper specialityMapper) {
        this.specialityRepository = specialityRepository;
        this.specialityMapper = specialityMapper;
    }

    /**
     * Save a speciality.
     *
     * @param specialityDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SpecialityDTO save(SpecialityDTO specialityDTO) {
        log.debug("Request to save Speciality : {}", specialityDTO);
        Speciality speciality = specialityMapper.toEntity(specialityDTO);
        speciality = specialityRepository.save(speciality);
        return specialityMapper.toDto(speciality);
    }

    /**
     * Get all the specialities.
     *
     * @return the list of entities.
     */
    @Override
    public List<SpecialityDTO> findAll() {
        log.debug("Request to get all Specialities");
        return specialityRepository.findAll().stream()
            .map(specialityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one speciality by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<SpecialityDTO> findOne(UUID id) {
        log.debug("Request to get Speciality : {}", id);
        return specialityRepository.findById(id)
            .map(specialityMapper::toDto);
    }

    /**
     * Delete the speciality by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Speciality : {}", id);
        specialityRepository.deleteById(id);
    }
}
