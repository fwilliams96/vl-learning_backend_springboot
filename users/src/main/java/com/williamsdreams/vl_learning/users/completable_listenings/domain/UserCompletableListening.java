package com.williamsdreams.vl_learning.users.completable_listenings.domain;

import com.williamsdreams.vl_learning.shared.domain.Audio;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserCompletableListening extends NewUserCompletableListening {

    private UUID id;
    private String title;
    private boolean finished;
    private Audio audio;
    private List<CompletableUserListeningSentenceWord> words;
    private UUID userId;

    @Data
    @Builder
    public static class CompletableUserListeningSentenceWord {
        private String text;
        private boolean readOnly;
        private String response;
        private boolean correct;
    }

}
