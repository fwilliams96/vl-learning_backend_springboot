package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.users.application.find.UserFinder;
import com.williamsdreams.vl_learning.users_app.api.UserGetV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserGetV1Controller implements UserGetV1Api {

    private final UserFinder userFinder;

    @Override
    public ResponseEntity<UserDto> getUser(UUID userId) {
        return userFinder.find(userId).map(user -> ResponseEntity.ok(
            new UserDto(
                user.getEmail(),
                user.getName(),
                user.getId()
            )
        )).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
