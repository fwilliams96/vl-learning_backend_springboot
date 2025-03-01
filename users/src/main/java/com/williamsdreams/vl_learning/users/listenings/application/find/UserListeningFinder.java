package com.williamsdreams.vl_learning.users.listenings.application.find;

import com.williamsdreams.vl_learning.users.listenings.domain.UserListening;
import com.williamsdreams.vl_learning.users.listenings.domain.UserListeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserListeningFinder {

    private final UserListeningRepository userListeningRepository;

    public Optional<UserListening> findById(UUID id) {
        return userListeningRepository.findById(id);
    }

}
