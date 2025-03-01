package com.williamsdreams.vl_learning.users.completable_listenings.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class NewUserCompletableListening {

    private String topic;
    private UUID eventId;

}
