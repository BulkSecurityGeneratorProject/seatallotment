package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Institute;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the Institute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstituteRepository extends CassandraRepository<Institute, UUID> {

}
