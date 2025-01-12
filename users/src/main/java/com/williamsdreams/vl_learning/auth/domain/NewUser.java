package com.williamsdreams.vl_learning.auth.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class NewUser extends UserCredentials {

    private String externalId;
    private String name;

}
