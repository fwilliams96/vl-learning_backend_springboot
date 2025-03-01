package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.users.shared.domain.Audio;
import com.williamsdreams.vl_learning.users.completable_listenings.application.create.UserCompletableListeningCreator;
import com.williamsdreams.vl_learning.users.completable_listenings.domain.NewUserCompletableListening;
import com.williamsdreams.vl_learning.users.completable_listenings.domain.UserCompletableListening;
import com.williamsdreams.vl_learning.users_app.api.UserCompletableListeningsPostV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.CompletableUserListeningDto;
import com.williamsdreams.vl_learning.users_app.api.dto.CompletableUserListeningWordDto;
import com.williamsdreams.vl_learning.users_app.api.dto.NewCompletableUserListeningDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserCompletableListeningsPostV1Controller implements UserCompletableListeningsPostV1Api {

    private final UserCompletableListeningCreator userCompletableListeningCreator;

    @Override
    public ResponseEntity<CompletableUserListeningDto> createUserCompletableListening(UUID userId, NewCompletableUserListeningDto newCompletableUserListeningDto) {
        UserCompletableListening userCompletableListening = userCompletableListeningCreator.create(userId, mapCompletableUserListeningDto(newCompletableUserListeningDto));
        return ResponseEntity.ok(mapCompletableUserListeningToDto(userCompletableListening));
    }

    private CompletableUserListeningDto mapCompletableUserListeningToDto(UserCompletableListening userCompletableListening) {
        CompletableUserListeningDto completableUserListeningDto = new CompletableUserListeningDto();
        completableUserListeningDto.setId(userCompletableListening.getId());
        completableUserListeningDto.setTitle(userCompletableListening.getTitle());
        completableUserListeningDto.setWords(mapWordsToDto(userCompletableListening.getWords()));
        completableUserListeningDto.setFinished(userCompletableListening.isFinished());
        completableUserListeningDto.setAudio(mapAudioToDto(userCompletableListening.getAudio()));
        completableUserListeningDto.setUserId(userCompletableListening.getUserId());
        completableUserListeningDto.setTopic(userCompletableListening.getTopic());
        completableUserListeningDto.setEventId(userCompletableListening.getEventId());
        return completableUserListeningDto;
    }

    private String mapAudioToDto(Audio audio) {
        return audio.getContent();
    }

    private List<CompletableUserListeningWordDto> mapWordsToDto(List<UserCompletableListening.CompletableUserListeningSentenceWord> words) {
        if (CollectionUtils.isEmpty(words)) {
            return Collections.emptyList();
        }
        return words.stream()
                .map(this::mapWordToDto)
                .toList();
    }

    private CompletableUserListeningWordDto mapWordToDto(UserCompletableListening.CompletableUserListeningSentenceWord word) {
        CompletableUserListeningWordDto completableUserListeningWordDto = new CompletableUserListeningWordDto();
        completableUserListeningWordDto.setCorrect(word.isCorrect());
        completableUserListeningWordDto.setReadOnly(word.isReadOnly());
        completableUserListeningWordDto.setResponse(word.getResponse());
        completableUserListeningWordDto.setText(word.getText());
        return completableUserListeningWordDto;
    }

    private NewUserCompletableListening mapCompletableUserListeningDto(NewCompletableUserListeningDto newCompletableUserListeningDto) {
        return NewUserCompletableListening.builder()
                .topic(newCompletableUserListeningDto.getTopic())
                .eventId(newCompletableUserListeningDto.getEventId())
                .build();
    }
}
