package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SeatPartitionService;
import com.mycompany.myapp.domain.SeatPartition;
import com.mycompany.myapp.repository.SeatPartitionRepository;
import com.mycompany.myapp.service.dto.SeatPartitionDTO;
import com.mycompany.myapp.service.mapper.SeatPartitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SeatPartition}.
 */
@Service
public class SeatPartitionServiceImpl implements SeatPartitionService {

    private final Logger log = LoggerFactory.getLogger(SeatPartitionServiceImpl.class);

    private final SeatPartitionRepository seatPartitionRepository;

    private final SeatPartitionMapper seatPartitionMapper;

    public SeatPartitionServiceImpl(SeatPartitionRepository seatPartitionRepository, SeatPartitionMapper seatPartitionMapper) {
        this.seatPartitionRepository = seatPartitionRepository;
        this.seatPartitionMapper = seatPartitionMapper;
    }

    /**
     * Save a seatPartition.
     *
     * @param seatPartitionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SeatPartitionDTO save(SeatPartitionDTO seatPartitionDTO) {
        log.debug("Request to save SeatPartition : {}", seatPartitionDTO);
        SeatPartition seatPartition = seatPartitionMapper.toEntity(seatPartitionDTO);
        seatPartition = seatPartitionRepository.save(seatPartition);
        return seatPartitionMapper.toDto(seatPartition);
    }

    /**
     * Get all the seatPartitions.
     *
     * @return the list of entities.
     */
    @Override
    public List<SeatPartitionDTO> findAll() {
        log.debug("Request to get all SeatPartitions");
        return seatPartitionRepository.findAll().stream()
            .map(seatPartitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one seatPartition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<SeatPartitionDTO> findOne(UUID id) {
        log.debug("Request to get SeatPartition : {}", id);
        return seatPartitionRepository.findById(id)
            .map(seatPartitionMapper::toDto);
    }

    /**
     * Delete the seatPartition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(UUID id) {
        log.debug("Request to delete SeatPartition : {}", id);
        seatPartitionRepository.deleteById(id);
    }
}
