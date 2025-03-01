package com.williamsdreams.vl_learning.users.descriptions.infrastructure.persistence.postgresql.entity;

import com.williamsdreams.vl_learning.users.shared.infrastructure.persistence.UuidIdentifiedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_descriptions")
public class UserDescriptionEntity extends UuidIdentifiedEntity {

    private String title;

    private String topic;
    @Column(length = 5000000)
    private String image;
    private UUID eventId;
    private UUID userId;

}
