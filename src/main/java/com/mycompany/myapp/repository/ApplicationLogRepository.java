package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ApplicationLog;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the ApplicationLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationLogRepository extends CassandraRepository<ApplicationLog, UUID> {

}
