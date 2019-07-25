package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SeatPartition;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the SeatPartition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeatPartitionRepository extends CassandraRepository<SeatPartition, UUID> {

}
