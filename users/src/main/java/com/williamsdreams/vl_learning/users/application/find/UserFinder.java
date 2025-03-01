package com.williamsdreams.vl_learning.users.application.find;

import com.williamsdreams.vl_learning.users.domain.User;
import com.williamsdreams.vl_learning.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository userRepository;

    public Optional<User> find(UUID userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByExternalId(String externalId) {
        return userRepository.findByExternalId(externalId);
    }

}
