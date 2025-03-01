package com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity;

import com.williamsdreams.vl_learning.users.shared.infrastructure.persistence.UuidIdentifiedEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "user_reading_questions")
public class UserReadingQuestionEntity extends UuidIdentifiedEntity {

    private String question;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReadingQuestionOptionEntity> options;

    @ManyToOne
    @JoinColumn(name = "user_reading_id")
    private UserReadingEntity userReading;

    @Override
    public String toString() {
        return "UserReadingQuestionEntity{" +
                "question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}
