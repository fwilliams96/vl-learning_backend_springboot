package com.williamsdreams.vl_learning.users.completable_listenings.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CompletableListening extends NewUserCompletableListening {

    private UUID id;
    private String title;
    private String text;

}
