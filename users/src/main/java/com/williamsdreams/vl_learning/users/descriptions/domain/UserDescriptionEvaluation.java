package com.williamsdreams.vl_learning.users.descriptions.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDescriptionEvaluation {

    private String feedback;
    private Double score;
    private String proposedDescription;

}
