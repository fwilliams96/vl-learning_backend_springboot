package com.williamsdreams.vl_learning.app.controllers;

import com.williamsdreams.vl_learning.users.descriptions.application.find.UserDescriptionFinder;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescription;
import com.williamsdreams.vl_learning.app.api.UserDescriptionGetV1Api;
import com.williamsdreams.vl_learning.app.api.dto.UserDescriptionDto;
import com.williamsdreams.vl_learning.app.mappers.UserDescriptionToUserDescriptionDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserDescriptionGetV1Controller implements UserDescriptionGetV1Api {

    private final UserDescriptionFinder userDescriptionFinder;
    private final UserDescriptionToUserDescriptionDtoMapper userDescriptionToUserDescriptionDtoMapper;

    @Override
    public ResponseEntity<UserDescriptionDto> getUserDescription(UUID userId, UUID descriptionId) {
        return userDescriptionFinder.find(userId, descriptionId)
                .map(userDescription -> ResponseEntity.ok(mapUserDescriptionToDto(userDescription)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserDescriptionDto mapUserDescriptionToDto(UserDescription userDescription) {
        return userDescriptionToUserDescriptionDtoMapper.map(userDescription);
    }
}
