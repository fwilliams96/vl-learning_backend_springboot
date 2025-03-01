package com.williamsdreams.vl_learning.users.descriptions.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
public class NewUserDescription {

    private String topic;
    private UUID eventId;

}
