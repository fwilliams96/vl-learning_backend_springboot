package com.williamsdreams.vl_learning.auth.infrastructure.persistence.postgresql.repository;

import com.williamsdreams.vl_learning.auth.infrastructure.persistence.postgresql.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataPostgresqlUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByExternalId(String externalId);

}
