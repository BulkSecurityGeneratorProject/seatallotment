package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ApplicationlogService;
import com.mycompany.myapp.domain.Applicationlog;
import com.mycompany.myapp.repository.ApplicationlogRepository;
import com.mycompany.myapp.service.dto.ApplicationlogDTO;
import com.mycompany.myapp.service.mapper.ApplicationlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Applicationlog}.
 */
@Service
public class ApplicationlogServiceImpl implements ApplicationlogService {

    private final Logger log = LoggerFactory.getLogger(ApplicationlogServiceImpl.class);

    private final ApplicationlogRepository applicationlogRepository;

    private final ApplicationlogMapper applicationlogMapper;

    public ApplicationlogServiceImpl(ApplicationlogRepository applicationlogRepository, ApplicationlogMapper applicationlogMapper) {
        this.applicationlogRepository = applicationlogRepository;
        this.applicationlogMapper = applicationlogMapper;
    }

    /**
     * Save a applicationlog.
     *
     * @param applicationlogDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplicationlogDTO save(ApplicationlogDTO applicationlogDTO) {
        log.debug("Request to save Applicationlog : {}", applicationlogDTO);
        Applicationlog applicationlog = applicationlogMapper.toEntity(applicationlogDTO);
        applicationlog = applicationlogRepository.save(applicationlog);
        return applicationlogMapper.toDto(applicationlog);
    }

    /**
     * Get all the applicationlogs.
     *
     * @return the list of entities.
     */
    @Override
    public List<ApplicationlogDTO> findAll() {
        log.debug("Request to get all Applicationlogs");
        return applicationlogRepository.findAll().stream()
            .map(applicationlogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one applicationlog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ApplicationlogDTO> findOne(UUID id) {
        log.debug("Request to get Applicationlog : {}", id);
        return applicationlogRepository.findById(id)
            .map(applicationlogMapper::toDto);
    }

    /**
     * Delete the applicationlog by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Applicationlog : {}", id);
        applicationlogRepository.deleteById(id);
    }
}
