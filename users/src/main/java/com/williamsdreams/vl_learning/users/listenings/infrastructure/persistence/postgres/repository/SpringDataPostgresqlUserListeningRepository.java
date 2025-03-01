package com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.repository;

import com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.entity.UserListeningEntity;
import com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity.UserReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataPostgresqlUserListeningRepository extends JpaRepository<UserListeningEntity, UUID> {

}
