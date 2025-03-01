package com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity;

import com.williamsdreams.vl_learning.shared.infrastructure.persistence.UuidIdentifiedEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "user_readings")
public class UserReadingEntity extends UuidIdentifiedEntity {

    private String title;

    @Column(length = 5000)
    private String text;

    private String topic;

    @OneToMany(mappedBy = "userReading", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReadingQuestionEntity> questions;
    private UUID eventId;
    private UUID userId;

    @Override
    public String toString() {
        return "UserReadingEntity{" +
                "text='" + text + '\'' +
                ", topic='" + topic + '\'' +
                ", questions=" + questions +
                ", eventId=" + eventId +
                ", userId=" + userId +
                '}';
    }
}
