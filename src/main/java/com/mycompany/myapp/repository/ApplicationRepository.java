package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Application;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the Application entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationRepository extends CassandraRepository<Application, UUID> {

}
