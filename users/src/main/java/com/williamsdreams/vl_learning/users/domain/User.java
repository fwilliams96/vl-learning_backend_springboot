package com.williamsdreams.vl_learning.users.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
public class User extends NewUser {

    private UUID id;

}
