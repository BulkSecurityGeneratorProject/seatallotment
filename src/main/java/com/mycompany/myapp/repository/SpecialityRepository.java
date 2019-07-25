package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Speciality;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the Speciality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialityRepository extends CassandraRepository<Speciality, UUID> {

}
