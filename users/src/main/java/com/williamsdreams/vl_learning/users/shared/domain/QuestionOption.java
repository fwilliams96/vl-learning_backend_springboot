package com.williamsdreams.vl_learning.users.shared.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionOption {

    private String text;
    private boolean correct;

}
