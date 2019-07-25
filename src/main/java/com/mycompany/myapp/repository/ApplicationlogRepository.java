package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Applicationlog;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the Applicationlog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationlogRepository extends CassandraRepository<Applicationlog, UUID> {

}
