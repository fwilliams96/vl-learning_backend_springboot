package com.williamsdreams.vl_learning.auth.infrastructure.persistence.postgresql.model;

import com.williamsdreams.vl_learning.shared.infrastructure.persistence.UuidIdentifiedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class UserEntity extends UuidIdentifiedEntity {

    private String externalId;
    private String name;
    private String email;
    private String password;

}
