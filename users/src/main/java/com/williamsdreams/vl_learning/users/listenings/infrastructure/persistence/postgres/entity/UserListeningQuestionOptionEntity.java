package com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.entity;

import com.williamsdreams.vl_learning.shared.infrastructure.persistence.UuidIdentifiedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "user_listening_question_options")
public class UserListeningQuestionOptionEntity extends UuidIdentifiedEntity {

    private String text;
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private UserListeningQuestionEntity question;

    @Override
    public String toString() {
        return "UserListeningQuestionOptionEntity{" +
                "text='" + text + '\'' +
                ", correct=" + correct +
                '}';
    }
}
