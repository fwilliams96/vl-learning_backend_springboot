package com.williamsdreams.vl_learning.users.descriptions.infrastructure.persistence.postgresql.repository;

import com.williamsdreams.vl_learning.users.descriptions.infrastructure.persistence.postgresql.entity.UserDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataPostgresqlUserDescriptionRepository extends JpaRepository<UserDescriptionEntity, UUID> {

}
