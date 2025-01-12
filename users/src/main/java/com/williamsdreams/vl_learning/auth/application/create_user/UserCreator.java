package com.williamsdreams.vl_learning.auth.application.create_user;

import com.williamsdreams.vl_learning.auth.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCreator {

    private final UserRepository userRepository;

    public User create(NewUser user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            throw new UserAlreadyExistsError(user.getEmail());
        }
        return userRepository.create(
                User.builder()
                        .id(UUID.randomUUID())
                        .externalId(user.getExternalId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build()
        );
    }

}
