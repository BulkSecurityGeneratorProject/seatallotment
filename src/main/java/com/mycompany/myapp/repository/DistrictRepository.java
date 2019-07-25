package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.District;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the District entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictRepository extends CassandraRepository<District, UUID> {

}
