package com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres;

import com.williamsdreams.vl_learning.users.readings.domain.UserReading;
import com.williamsdreams.vl_learning.users.readings.domain.UserReadingRepository;
import com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity.UserReadingEntity;
import com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity.UserReadingQuestionEntity;
import com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity.UserReadingQuestionOptionEntity;
import com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.repository.SpringDataPostgresqlUserReadingRepository;
import com.williamsdreams.vl_learning.users.shared.domain.Question;
import com.williamsdreams.vl_learning.users.shared.domain.QuestionOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostgresUserReadingRepository implements UserReadingRepository {

    private final SpringDataPostgresqlUserReadingRepository springDataPostgresqlUserReadingRepository;

    @Override
    public Optional<UserReading> findById(UUID id) {
        Optional<UserReadingEntity> userReadingEntity = springDataPostgresqlUserReadingRepository.findById(id);
        return userReadingEntity.map(this::mapUserReadingEntityToUserReading);
    }

    @Override
    public UserReading save(UserReading userReading) {
        UserReadingEntity save = springDataPostgresqlUserReadingRepository.save(mapUserReadingToUserReadingEntity(userReading));
        return mapUserReadingEntityToUserReading(save);
    }

    private UserReadingEntity mapUserReadingToUserReadingEntity(UserReading userReading) {
        UserReadingEntity userReadingEntity = new UserReadingEntity();
        userReadingEntity.setId(userReading.getId());
        userReadingEntity.setTitle(userReading.getTitle());
        userReadingEntity.setTopic(userReading.getTopic());
        userReadingEntity.setText(userReading.getText());
        userReadingEntity.setQuestions(mapQuestionsToEntity(userReading.getQuestions(), userReadingEntity));
        userReadingEntity.setUserId(userReading.getUserId());
        userReadingEntity.setEventId(userReading.getEventId());
        return userReadingEntity;
    }

    private List<UserReadingQuestionEntity> mapQuestionsToEntity(List<Question> questions, UserReadingEntity userReadingEntity) {
        if (CollectionUtils.isEmpty(questions)) {
            return Collections.emptyList();
        }
        return questions.stream()
                .map(question -> {
                    UserReadingQuestionEntity userReadingQuestionEntity = new UserReadingQuestionEntity();
                    userReadingQuestionEntity.setId(UUID.randomUUID());
                    userReadingQuestionEntity.setQuestion(question.getQuestion());
                    userReadingQuestionEntity.setOptions(mapQuestionOptionsToEntity(question.getOptions(), userReadingQuestionEntity));
                    userReadingQuestionEntity.setUserReading(userReadingEntity);
                    return userReadingQuestionEntity;
                })
                .toList();
    }

    private List<UserReadingQuestionOptionEntity> mapQuestionOptionsToEntity(List<QuestionOption> options, UserReadingQuestionEntity userReadingQuestionEntity) {
        if (CollectionUtils.isEmpty(options)) {
            return Collections.emptyList();
        }
        return options.stream()
                .map(option -> {
                    UserReadingQuestionOptionEntity userReadingQuestionOptionEntity = new UserReadingQuestionOptionEntity();
                    userReadingQuestionOptionEntity.setId(UUID.randomUUID());
                    userReadingQuestionOptionEntity.setText(option.getText());
                    userReadingQuestionOptionEntity.setCorrect(option.isCorrect());
                    userReadingQuestionOptionEntity.setQuestion(userReadingQuestionEntity);
                    return userReadingQuestionOptionEntity;
                })
                .toList();
    }

    private UserReading mapUserReadingEntityToUserReading(UserReadingEntity userReadingEntity) {
        return UserReading.builder()
                .id(userReadingEntity.getId())
                .title(userReadingEntity.getTitle())
                .topic(userReadingEntity.getTopic())
                .text(userReadingEntity.getText())
                .questions(mapQuestionsToDomain(userReadingEntity.getQuestions()))
                .userId(userReadingEntity.getUserId())
                .eventId(userReadingEntity.getEventId())
                .build();
    }

    private List<Question> mapQuestionsToDomain(List<UserReadingQuestionEntity> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            return Collections.emptyList();
        }
        return questions.stream()
                .map(question -> {
                    Question.QuestionBuilder questionBuilder = Question.builder()
                            .question(question.getQuestion());
                    if (!CollectionUtils.isEmpty(question.getOptions())) {
                        questionBuilder.options(mapQuestionOptionsToDomain(question.getOptions()));
                    }
                    return questionBuilder.build();
                })
                .toList();
    }

    private List<QuestionOption> mapQuestionOptionsToDomain(List<UserReadingQuestionOptionEntity> options) {
        if (CollectionUtils.isEmpty(options)) {
            return Collections.emptyList();
        }
        return options.stream()
                .map(option -> QuestionOption.builder()
                        .text(option.getText())
                        .correct(option.isCorrect())
                        .build())
                .toList();
    }

}
