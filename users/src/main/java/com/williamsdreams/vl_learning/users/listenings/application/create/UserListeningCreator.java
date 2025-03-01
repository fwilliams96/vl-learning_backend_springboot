package com.williamsdreams.vl_learning.users.listenings.application.create;

import com.williamsdreams.vl_learning.users.shared.domain.Audio;
import com.williamsdreams.vl_learning.users.shared.domain.TextToSpeechGateway;
import com.williamsdreams.vl_learning.users.listenings.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserListeningCreator {

    private final UserListeningRepository userListeningRepository;
    private final UserListeningGeneratorGateway userListeningGeneratorGateway;
    private final TextToSpeechGateway textToSpeechGateway;

    public UserListening create(UUID userId, NewUserListening newUserListening) {

        Listening listening = userListeningGeneratorGateway.generateListening(newUserListening.getTopic());
        log.info("Generated listening: {}", listening);
        Audio audio = textToSpeechGateway.textToSpeech(listening.getText());
        return userListeningRepository.create(
                UserListening.builder()
                        .id(UUID.randomUUID())
                        .title(listening.getTitle())
                        .topic(newUserListening.getTopic())
                        .text(listening.getText())
                        .audio(audio)
                        .questions(listening.getQuestions())
                        .userId(userId)
                        .eventId(newUserListening.getEventId())
                        .build()
        );
    }


}
