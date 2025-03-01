package com.williamsdreams.vl_learning.users.readings.infrastructure.ai.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReadingGeneration {

    @JsonProperty(required = true, value = "title")
    private String title;

    @JsonProperty(required = true, value = "comments", defaultValue = "")
    private String comments;

    @JsonProperty(required = true, value = "text", defaultValue = "")
    private String text;

    @JsonProperty(required = true, value = "questions")
    private List<ReadingGenerationQuestion> questions;

    @Data
    public static class ReadingGenerationQuestion {

        @JsonProperty(required = true, value = "question")
        private String question;

        @JsonProperty(required = true, value = "options")
        private List<ReadingGenerationOption> options;
    }

    @Data
    public static class ReadingGenerationOption {
        @JsonProperty(required = true, value = "text")
        private String text;

        @JsonProperty(required = true, value = "correct", defaultValue = "false")
        private boolean correct;
    }

}
