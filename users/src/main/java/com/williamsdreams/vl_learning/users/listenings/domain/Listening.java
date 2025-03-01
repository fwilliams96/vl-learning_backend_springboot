package com.williamsdreams.vl_learning.users.listenings.domain;

import com.williamsdreams.vl_learning.users.shared.domain.Question;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class Listening {

    private String title;
    private String text;
    private List<Question> questions;

}
