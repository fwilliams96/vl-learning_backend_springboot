package com.williamsdreams.vl_learning.users.readings.domain;

import com.williamsdreams.vl_learning.users.shared.domain.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserReading extends NewUserReading {

    private UUID id;
    private String title;
    private String text;
    private List<Question> questions;
    private UUID userId;

}
