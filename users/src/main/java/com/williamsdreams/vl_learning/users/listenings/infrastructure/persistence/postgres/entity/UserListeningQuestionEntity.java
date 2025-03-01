package com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.entity;

import com.williamsdreams.vl_learning.shared.infrastructure.persistence.UuidIdentifiedEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "user_listening_questions")
public class UserListeningQuestionEntity extends UuidIdentifiedEntity {

    private String question;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserListeningQuestionOptionEntity> options;

    @ManyToOne
    @JoinColumn(name = "user_listening_id")
    private UserListeningEntity userListening;

    @Override
    public String toString() {
        return "UserListeningQuestionEntity{" +
                "question='" + question + '\'' +
                ", options=" + options +
                '}';
    }

}
