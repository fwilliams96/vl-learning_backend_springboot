package com.williamsdreams.vl_learning.users.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class NewUser {

    private String email;
    private String name;

}
