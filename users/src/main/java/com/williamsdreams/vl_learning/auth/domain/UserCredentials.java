package com.williamsdreams.vl_learning.auth.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserCredentials {

    private String email;
    private String password;

}
