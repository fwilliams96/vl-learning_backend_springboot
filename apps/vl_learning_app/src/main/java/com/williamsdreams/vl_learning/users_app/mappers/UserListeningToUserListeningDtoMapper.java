package com.williamsdreams.vl_learning.users_app.mappers;

import com.williamsdreams.vl_learning.users.listenings.domain.UserListening;
import com.williamsdreams.vl_learning.users.shared.domain.Question;
import com.williamsdreams.vl_learning.users.shared.domain.QuestionOption;
import com.williamsdreams.vl_learning.users_app.api.dto.QuestionDto;
import com.williamsdreams.vl_learning.users_app.api.dto.QuestionOptionDto;
import com.williamsdreams.vl_learning.users_app.api.dto.UserListeningDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
public class UserListeningToUserListeningDtoMapper {

    public UserListeningDto map(UserListening userListening) {
        UserListeningDto userListeningDto = new UserListeningDto();
        userListeningDto.setId(userListening.getId());
        userListeningDto.setTitle(userListening.getTitle());
        userListeningDto.setUserId(userListening.getUserId());
        userListeningDto.setText(userListening.getText());
        userListeningDto.setAudio(userListening.getAudio().getContent());
        userListeningDto.setTopic(userListening.getTopic());
        userListeningDto.setQuestions(mapQuestionsToDto(userListening.getQuestions()));
        userListeningDto.setEventId(userListening.getEventId());
        return userListeningDto;
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
