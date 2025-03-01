package com.williamsdreams.vl_learning.users.completable_listenings.infrastructure.ai.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompletableListeningGeneration {

    @JsonProperty(required = true, value = "title", defaultValue = "")
    private String title;

    @JsonProperty(required = true, value = "comments", defaultValue = "")
    private String comments;

    @JsonProperty(required = true, value = "text", defaultValue = "")
    private String text;

}
