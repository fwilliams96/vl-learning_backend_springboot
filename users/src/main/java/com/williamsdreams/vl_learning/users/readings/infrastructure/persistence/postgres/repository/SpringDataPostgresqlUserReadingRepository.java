package com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.repository;

import com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity.UserReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataPostgresqlUserReadingRepository extends JpaRepository<UserReadingEntity, UUID> {

}
