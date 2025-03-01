package com.williamsdreams.vl_learning.users.descriptions.infrastructure.ai.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DescriptionEvaluation {

    @JsonProperty(required = true, value = "score")
    private double score;

    @JsonProperty(required = true, value = "feedback")
    private String feedback;

    @JsonProperty(required = true, value = "proposed_description")
    private String proposedDescription;

}
