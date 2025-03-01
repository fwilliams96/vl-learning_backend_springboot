package com.williamsdreams.vl_learning.users.completable_listenings.application.create;

import com.williamsdreams.vl_learning.shared.domain.Audio;
import com.williamsdreams.vl_learning.shared.domain.TextToSpeechGateway;
import com.williamsdreams.vl_learning.users.completable_listenings.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCompletableListeningCreator {

    private final UserCompletableListeningRepository userCompletableListeningRepository;
    private final UserCompletableListeningGeneratorGateway userCompletableListeningGeneratorGateway;
    private final TextToSpeechGateway textToSpeechGateway;

    public UserCompletableListening create(UUID userId, NewUserCompletableListening newUserCompletableListening) {
        CompletableListening completableListening = userCompletableListeningGeneratorGateway.generateCompletableListening(newUserCompletableListening.getTopic());
        log.info("Generated completable listening: {}", completableListening);
        Audio audio = textToSpeechGateway.textToSpeech(completableListening.getText());
        return userCompletableListeningRepository.create(
                UserCompletableListening.builder()
                        .id(UUID.randomUUID())
                        .title(completableListening.getTitle())
                        .topic(newUserCompletableListening.getTopic())
                        .finished(false)
                        .audio(audio)
                        .words(generateWords(completableListening.getText()))
                        .userId(userId)
                        .eventId(newUserCompletableListening.getEventId())
                        .build()
        );
    }

    public List<UserCompletableListening.CompletableUserListeningSentenceWord> generateWords(String sentence) {
        String sentenceWithSpaces = sentence.replaceAll("([.,!?;])", " $1 ");

        String[] tokens = sentenceWithSpaces.split("\\s+");

        Set<String> punctuation = Set.of(".", ",", "!", "?", ";");

        List<String> words = new ArrayList<>();
        for (String token : tokens) {
            if (!punctuation.contains(token)) {
                words.add(token);
            }
        }

        int numWordsToAsk = Math.max(1, words.size() / 5);

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        Set<String> wordsPicked = new HashSet<>();
        for (int i = 0; i < numWordsToAsk; i++) {
            wordsPicked.add(words.get(indices.get(i)));
        }

        List<UserCompletableListening.CompletableUserListeningSentenceWord> result = new ArrayList<>();
        for (String token : tokens) {
            boolean isWord = !punctuation.contains(token);
            boolean readOnly = !isWord || !wordsPicked.contains(token);
            UserCompletableListening.CompletableUserListeningSentenceWord wordObj = UserCompletableListening.CompletableUserListeningSentenceWord.builder()
                    .text(token)
                    .readOnly(readOnly)
                    .response("")
                    .correct(false)
                    .build();
            result.add(wordObj);
        }

        return result;
    }

}
