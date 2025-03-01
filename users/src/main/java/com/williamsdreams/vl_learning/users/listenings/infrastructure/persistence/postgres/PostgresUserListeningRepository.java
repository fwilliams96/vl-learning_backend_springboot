package com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres;

import com.williamsdreams.vl_learning.shared.domain.Audio;
import com.williamsdreams.vl_learning.users.listenings.domain.UserListening;
import com.williamsdreams.vl_learning.users.listenings.domain.UserListeningRepository;
import com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.entity.UserListeningEntity;
import com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.entity.UserListeningQuestionEntity;
import com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.entity.UserListeningQuestionOptionEntity;
import com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.repository.SpringDataPostgresqlUserListeningRepository;
import com.williamsdreams.vl_learning.users.shared.domain.Question;
import com.williamsdreams.vl_learning.users.shared.domain.QuestionOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
@RequiredArgsConstructor
public class PostgresUserListeningRepository implements UserListeningRepository {

    private final SpringDataPostgresqlUserListeningRepository springDataPostgresqlUserListeningRepository;

    @Override
    public Optional<UserListening> findById(UUID id) {
        Optional<UserListeningEntity> byId = springDataPostgresqlUserListeningRepository.findById(id);
        return byId.map(this::mapUserListeningEntityToDomain);
    }

    @Override
    public UserListening create(UserListening userListening) {
        UserListeningEntity save = springDataPostgresqlUserListeningRepository.save(mapUserListeningToEntity(userListening));
        return mapUserListeningEntityToDomain(save);
    }

    private UserListening mapUserListeningEntityToDomain(UserListeningEntity listeningEntity) {
        return UserListening.builder()
                .id(listeningEntity.getId())
                .title(listeningEntity.getTitle())
                .text(listeningEntity.getText())
                .topic(listeningEntity.getTopic())
                .userId(listeningEntity.getUserId())
                .audio(Audio.builder().content(listeningEntity.getAudio()).build())
                .questions(mapQuestionsToDomain(listeningEntity.getQuestions()))
                .build();
    }

    private List<Question> mapQuestionsToDomain(List<UserListeningQuestionEntity> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            return Collections.emptyList();
        }
        return questions.stream()
                .map(this::mapQuestionToDomain)
                .toList();
    }

    private Question mapQuestionToDomain(UserListeningQuestionEntity userListeningQuestionEntity) {
        return Question.builder()
                .question(userListeningQuestionEntity.getQuestion())
                .options(mapOptionsToDomain(userListeningQuestionEntity.getOptions()))
                .build();
    }

    private List<QuestionOption> mapOptionsToDomain(List<UserListeningQuestionOptionEntity> options) {
        if (CollectionUtils.isEmpty(options)) {
            return Collections.emptyList();
        }
        return options.stream()
                .map(this::mapOptionToDomain)
                .toList();
    }

    private QuestionOption mapOptionToDomain(UserListeningQuestionOptionEntity userListeningQuestionOptionEntity) {
        return QuestionOption.builder()
                .text(userListeningQuestionOptionEntity.getText())
                .correct(userListeningQuestionOptionEntity.isCorrect())
                .build();
    }

    private UserListeningEntity mapUserListeningToEntity(UserListening userListening) {
        UserListeningEntity userListeningEntity = new UserListeningEntity();
        userListeningEntity.setId(userListening.getId());
        userListeningEntity.setTitle(userListening.getTitle());
        userListeningEntity.setText(userListening.getText());
        userListeningEntity.setTopic(userListening.getTopic());
        userListeningEntity.setUserId(userListening.getUserId());
        userListeningEntity.setAudio(userListening.getAudio().getContent());
        userListeningEntity.setQuestions(mapQuestionsToEntity(userListening.getQuestions(), userListeningEntity));
        userListeningEntity.setEventId(userListening.getEventId());
        return userListeningEntity;
    }

    private List<UserListeningQuestionEntity> mapQuestionsToEntity(List<Question> questions, UserListeningEntity userListeningEntity) {
        if (CollectionUtils.isEmpty(questions)) {
            return Collections.emptyList();
        }
        return questions.stream()
                .map(question -> mapQuestionToEntity(question, userListeningEntity))
                .toList();
    }

    private UserListeningQuestionEntity mapQuestionToEntity(Question question, UserListeningEntity userListeningEntity) {
        UserListeningQuestionEntity userListeningQuestionEntity = new UserListeningQuestionEntity();
        userListeningQuestionEntity.setId(UUID.randomUUID());
        userListeningQuestionEntity.setQuestion(question.getQuestion());
        userListeningQuestionEntity.setOptions(mapOptionsToEntity(question.getOptions(), userListeningQuestionEntity));
        userListeningQuestionEntity.setUserListening(userListeningEntity);
        return userListeningQuestionEntity;
    }

    private List<UserListeningQuestionOptionEntity> mapOptionsToEntity(List<QuestionOption> options, UserListeningQuestionEntity userListeningQuestionEntity) {
        if (CollectionUtils.isEmpty(options)) {
            return Collections.emptyList();
        }
        return options.stream()
                .map(option -> mapOptionToEntity(option, userListeningQuestionEntity))
                .toList();
    }

    private UserListeningQuestionOptionEntity mapOptionToEntity(QuestionOption option, UserListeningQuestionEntity userListeningQuestionEntity) {
        UserListeningQuestionOptionEntity userListeningQuestionOptionEntity = new UserListeningQuestionOptionEntity();
        userListeningQuestionOptionEntity.setId(UUID.randomUUID());
        userListeningQuestionOptionEntity.setText(option.getText());
        userListeningQuestionOptionEntity.setCorrect(option.isCorrect());
        userListeningQuestionOptionEntity.setQuestion(userListeningQuestionEntity);
        return userListeningQuestionOptionEntity;
    }
}
