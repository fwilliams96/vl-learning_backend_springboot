package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.users.listenings.application.find.UserListeningFinder;
import com.williamsdreams.vl_learning.users.listenings.domain.UserListening;
import com.williamsdreams.vl_learning.users_app.api.UserListeningGetV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.UserListeningDto;
import com.williamsdreams.vl_learning.users_app.mappers.UserListeningToUserListeningDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserListeningGetV1Controller implements UserListeningGetV1Api {

    private final UserListeningFinder userListeningFinder;
    private final UserListeningToUserListeningDtoMapper userListeningToUserListeningDtoMapper;

    @Override
    public ResponseEntity<UserListeningDto> getUserListening(UUID userId, UUID listeningId) {
        return userListeningFinder.findById(listeningId)
                .map(userListening -> ResponseEntity.ok(mapUserListeningToDto(userListening)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserListeningDto mapUserListeningToDto(UserListening userListening) {
        return userListeningToUserListeningDtoMapper.map(userListening);
    }
}
