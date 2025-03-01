package com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity;

import com.williamsdreams.vl_learning.shared.infrastructure.persistence.UuidIdentifiedEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "user_reading_question_options")
public class UserReadingQuestionOptionEntity extends UuidIdentifiedEntity {

    private String text;
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private UserReadingQuestionEntity question;

    @Override
    public String toString() {
        return "UserReadingQuestionOptionEntity{" +
                "text='" + text + '\'' +
                ", correct=" + correct +
                '}';
    }
}
