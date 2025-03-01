package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.users.readings.application.create.UserReadingCreator;
import com.williamsdreams.vl_learning.users.readings.domain.NewUserReading;
import com.williamsdreams.vl_learning.users.readings.domain.UserReading;
import com.williamsdreams.vl_learning.users_app.api.UserReadingsPostV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.NewUserReadingDto;
import com.williamsdreams.vl_learning.users_app.api.dto.UserReadingDto;
import com.williamsdreams.vl_learning.users_app.mappers.UserReadingToUserReadingDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserReadingsPostV1Controller implements UserReadingsPostV1Api {

    private final UserReadingCreator userReadingCreator;
    private final UserReadingToUserReadingDtoMapper userReadingToUserReadingDtoMapper;

    @Override
    public ResponseEntity<UserReadingDto> createUserReading(UUID userId, NewUserReadingDto newUserReadingDto) {
        UserReading userReading = userReadingCreator.create(userId, mapUserReadingDto(newUserReadingDto));
        return ResponseEntity.ok(mapUserReadingToDto(userReading));
    }

    private UserReadingDto mapUserReadingToDto(UserReading userReading) {
        return userReadingToUserReadingDtoMapper.map(userReading);
    }

    private NewUserReading mapUserReadingDto(NewUserReadingDto newUserReadingDto) {
        return NewUserReading.builder()
                .topic(newUserReadingDto.getTopic())
                .eventId(newUserReadingDto.getEventId())
                .build();
    }
}
