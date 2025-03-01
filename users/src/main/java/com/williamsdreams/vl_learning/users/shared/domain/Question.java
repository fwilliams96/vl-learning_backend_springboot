package com.williamsdreams.vl_learning.users.shared.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Question {

    private String question;
    private List<QuestionOption> options;

}
