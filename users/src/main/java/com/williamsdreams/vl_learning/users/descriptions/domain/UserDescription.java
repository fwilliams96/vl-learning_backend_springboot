package com.williamsdreams.vl_learning.users.descriptions.domain;

import com.williamsdreams.vl_learning.users.shared.domain.Image;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
public class UserDescription extends NewUserDescription {

    private UUID id;
    private String title;
    private Image image;
    private UUID userId;

}
