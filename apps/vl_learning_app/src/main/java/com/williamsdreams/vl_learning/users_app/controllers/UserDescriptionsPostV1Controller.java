package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.users.descriptions.application.create.UserDescriptionCreator;
import com.williamsdreams.vl_learning.users.descriptions.domain.NewUserDescription;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescription;
import com.williamsdreams.vl_learning.users_app.api.UserDescriptionsPostV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.NewUserDescriptionDto;
import com.williamsdreams.vl_learning.users_app.api.dto.UserDescriptionDto;
import com.williamsdreams.vl_learning.users_app.mappers.UserDescriptionToUserDescriptionDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserDescriptionsPostV1Controller implements UserDescriptionsPostV1Api {

    private final UserDescriptionCreator userDescriptionCreator;
    private final UserDescriptionToUserDescriptionDtoMapper userDescriptionToUserDescriptionDtoMapper;

    @Override
    public ResponseEntity<UserDescriptionDto> createUserDescription(UUID userId, NewUserDescriptionDto newUserDescriptionDto) {
        UserDescription userDescription = userDescriptionCreator.create(userId, mapToNewUserDescription(newUserDescriptionDto));
        return ResponseEntity.ok(mapToUserDescriptionDto(userDescription));
    }

    private UserDescriptionDto mapToUserDescriptionDto(UserDescription userDescription) {
        return userDescriptionToUserDescriptionDtoMapper.map(userDescription);
    }

    private NewUserDescription mapToNewUserDescription(NewUserDescriptionDto newUserDescriptionDto) {
        return NewUserDescription.builder()
                .topic(newUserDescriptionDto.getTopic())
                .eventId(newUserDescriptionDto.getEventId())
                .build();
    }
}
