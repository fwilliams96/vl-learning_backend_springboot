package com.williamsdreams.vl_learning.users.readings.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class NewUserReading {

    private String topic;
    private UUID eventId;

}
