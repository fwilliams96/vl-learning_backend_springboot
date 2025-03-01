package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.users.application.create_user.UserCreator;
import com.williamsdreams.vl_learning.users.domain.NewUser;
import com.williamsdreams.vl_learning.users.domain.User;
import com.williamsdreams.vl_learning.users_app.api.UsersCreatePostV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.NewUserDto;
import com.williamsdreams.vl_learning.users_app.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UsersCreatePostV1Controller implements UsersCreatePostV1Api {

    private final UserCreator userCreator;

    @Override
    public ResponseEntity<UserDto> postUsersCreate(String xUserId, NewUserDto newUserDto) {
        User user = userCreator.create(
                UUID.fromString(xUserId),
                NewUser.builder()
                        .email(newUserDto.getEmail())
                        .name(newUserDto.getName())
                        .build()
        );
        return ResponseEntity.ok(
            new UserDto(
                user.getEmail(),
                user.getName(),
                user.getId()
            )
        );
    }
}
