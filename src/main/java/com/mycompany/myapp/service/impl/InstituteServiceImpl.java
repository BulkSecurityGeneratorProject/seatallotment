package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.InstituteService;
import com.mycompany.myapp.domain.Institute;
import com.mycompany.myapp.repository.InstituteRepository;
import com.mycompany.myapp.service.dto.InstituteDTO;
import com.mycompany.myapp.service.mapper.InstituteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Institute}.
 */
@Service
public class InstituteServiceImpl implements InstituteService {

    private final Logger log = LoggerFactory.getLogger(InstituteServiceImpl.class);

    private final InstituteRepository instituteRepository;

    private final InstituteMapper instituteMapper;

    public InstituteServiceImpl(InstituteRepository instituteRepository, InstituteMapper instituteMapper) {
        this.instituteRepository = instituteRepository;
        this.instituteMapper = instituteMapper;
    }

    /**
     * Save a institute.
     *
     * @param instituteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InstituteDTO save(InstituteDTO instituteDTO) {
        log.debug("Request to save Institute : {}", instituteDTO);
        Institute institute = instituteMapper.toEntity(instituteDTO);
        institute = instituteRepository.save(institute);
        return instituteMapper.toDto(institute);
    }

    /**
     * Get all the institutes.
     *
     * @return the list of entities.
     */
    @Override
    public List<InstituteDTO> findAll() {
        log.debug("Request to get all Institutes");
        return instituteRepository.findAll().stream()
            .map(instituteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one institute by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<InstituteDTO> findOne(UUID id) {
        log.debug("Request to get Institute : {}", id);
        return instituteRepository.findById(id)
            .map(instituteMapper::toDto);
    }

    /**
     * Delete the institute by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Institute : {}", id);
        instituteRepository.deleteById(id);
    }
}
