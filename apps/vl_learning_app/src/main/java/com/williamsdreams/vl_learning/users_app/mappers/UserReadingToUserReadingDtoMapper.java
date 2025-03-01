package com.williamsdreams.vl_learning.users_app.mappers;

import com.williamsdreams.vl_learning.users.readings.domain.UserReading;
import com.williamsdreams.vl_learning.users.shared.domain.Question;
import com.williamsdreams.vl_learning.users.shared.domain.QuestionOption;
import com.williamsdreams.vl_learning.users_app.api.dto.QuestionDto;
import com.williamsdreams.vl_learning.users_app.api.dto.QuestionOptionDto;
import com.williamsdreams.vl_learning.users_app.api.dto.UserReadingDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
public class UserReadingToUserReadingDtoMapper {

    public UserReadingDto map(UserReading userReading) {
        UserReadingDto userReadingDto = new UserReadingDto();
        userReadingDto.setId(userReading.getId());
        userReadingDto.setTitle(userReading.getTitle());
        userReadingDto.setUserId(userReading.getUserId());
        userReadingDto.setText(userReading.getText());
        userReadingDto.setTopic(userReading.getTopic());
        userReadingDto.setQuestions(mapQuestionsToDto(userReading.getQuestions()));
        userReadingDto.setEventId(userReading.getEventId());
        return userReadingDto;
    }

    private List<QuestionDto> mapQuestionsToDto(List<Question> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            return Collections.emptyList();
        }
        return questions.stream()
                .map(this::mapQuestionToDto)
                .toList();
    }

    private QuestionDto mapQuestionToDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion(question.getQuestion());
        questionDto.setOptions(mapOptionsToDto(question.getOptions()));
        return questionDto;
    }

    private List<QuestionOptionDto> mapOptionsToDto(List<QuestionOption> options) {
        if (CollectionUtils.isEmpty(options)) {
            return Collections.emptyList();
        }
        return options.stream()
                .map(this::mapOptionToDto)
                .toList();
    }

    private QuestionOptionDto mapOptionToDto(QuestionOption option) {
        QuestionOptionDto questionOptionDto = new QuestionOptionDto();
        questionOptionDto.setText(option.getText());
        questionOptionDto.setCorrect(option.isCorrect());
        return questionOptionDto;
    }

}
