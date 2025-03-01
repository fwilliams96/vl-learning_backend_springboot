package com.williamsdreams.vl_learning.users.listenings.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class NewUserListening {

    private String topic;
    private UUID eventId;

}
