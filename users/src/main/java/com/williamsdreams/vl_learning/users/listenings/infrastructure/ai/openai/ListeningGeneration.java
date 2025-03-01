package com.williamsdreams.vl_learning.users.listenings.infrastructure.ai.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListeningGeneration {

    @JsonProperty(required = true, value = "title")
    private String title;

    @JsonProperty(required = true, value = "comments")
    private String comments;

    @JsonProperty(required = true, value = "text")
    private String text;

    @JsonProperty(required = true, value = "questions")
    private List<ListeningGenerationQuestion> questions;

    @Data
    public static class ListeningGenerationQuestion {

        @JsonProperty(required = true, value = "question")
        private String question;

        @JsonProperty(required = true, value = "options")
        private List<ListeningGenerationOption> options;
    }

    @Data
    public static class ListeningGenerationOption {
        @JsonProperty(required = true, value = "text")
        private String text;

        @JsonProperty(required = true, value = "correct", defaultValue = "false")
        private boolean correct;
    }

}
