package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.users.listenings.application.create.UserListeningCreator;
import com.williamsdreams.vl_learning.users.listenings.domain.NewUserListening;
import com.williamsdreams.vl_learning.users.listenings.domain.UserListening;
import com.williamsdreams.vl_learning.users_app.api.UserListeningsPostV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.NewUserListeningDto;
import com.williamsdreams.vl_learning.users_app.api.dto.UserListeningDto;
import com.williamsdreams.vl_learning.users_app.mappers.UserListeningToUserListeningDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserListeningsPostV1Controller implements UserListeningsPostV1Api {

    private final UserListeningCreator userListeningCreator;
    private final UserListeningToUserListeningDtoMapper userListeningToUserListeningDtoMapper;

    @Override
    public ResponseEntity<UserListeningDto> createUserListening(UUID userId, NewUserListeningDto newUserListeningDto) {
        UserListening userListening = userListeningCreator.create(userId, mapUserListeningDto(newUserListeningDto));
        return ResponseEntity.ok(mapUserListeningToDto(userListening));
    }

    private UserListeningDto mapUserListeningToDto(UserListening userListening) {
        return userListeningToUserListeningDtoMapper.map(userListening);
    }

    private NewUserListening mapUserListeningDto(NewUserListeningDto newUserListeningDto) {
        return NewUserListening.builder()
                .topic(newUserListeningDto.getTopic())
                .eventId(newUserListeningDto.getEventId())
                .build();
    }
}
