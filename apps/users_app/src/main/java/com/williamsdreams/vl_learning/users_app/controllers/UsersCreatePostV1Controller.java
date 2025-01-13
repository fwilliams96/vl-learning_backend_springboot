package com.williamsdreams.vl_learning.users_app.controllers;

import com.williamsdreams.vl_learning.auth.application.create_user.UserCreator;
import com.williamsdreams.vl_learning.auth.domain.NewUser;
import com.williamsdreams.vl_learning.auth.domain.User;
import com.williamsdreams.vl_learning.users_app.api.UsersCreatePostV1Api;
import com.williamsdreams.vl_learning.users_app.api.dto.NewUserDto;
import com.williamsdreams.vl_learning.users_app.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersCreatePostV1Controller implements UsersCreatePostV1Api {

    private final UserCreator userCreator;

    @Override
    public ResponseEntity<UserDto> postUsersCreate(NewUserDto newUserDto) {
        User user = userCreator.create(
                NewUser.builder()
                        .externalId(newUserDto.getExternalId())
                        .email(newUserDto.getEmail())
                        .name(newUserDto.getName())
                        .password(newUserDto.getPassword())
                        .build()
        );
        return ResponseEntity.ok(
                new UserDto(
                        user.getEmail(),
                        user.getPassword(),
                        user.getExternalId(),
                        user.getName(),
                        user.getId()
                )
        );
    }
}
