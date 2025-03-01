package com.williamsdreams.vl_learning.users.readings.domain;

import com.williamsdreams.vl_learning.users.shared.domain.Question;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Reading {

    private String title;
    private String text;
    private List<Question> questions;

}
