package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.users.readings.application.finder.UserReadingFinder;
import com.williamsdreams.vl_learning.users.readings.domain.UserReading;
import com.williamsdreams.vl_learning.users_app.api.UserReadingGetV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.UserReadingDto;
import com.williamsdreams.vl_learning.users_app.mappers.UserReadingToUserReadingDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserReadingGetV1Controller implements UserReadingGetV1Api {

    private final UserReadingFinder userReadingFinder;
    private final UserReadingToUserReadingDtoMapper userReadingToUserReadingDtoMapper;

    @Override
    public ResponseEntity<UserReadingDto> getUserReading(UUID userId, UUID readingId) {
        return userReadingFinder.findById(readingId)
                .map(userReading -> ResponseEntity.ok(mapUserReadingToDto(userReading)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserReadingDto mapUserReadingToDto(UserReading userReading) {
        return userReadingToUserReadingDtoMapper.map(userReading);
    }
}
