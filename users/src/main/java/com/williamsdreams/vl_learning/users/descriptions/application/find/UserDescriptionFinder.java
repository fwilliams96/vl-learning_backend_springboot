package com.williamsdreams.vl_learning.users.descriptions.application.find;

import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescription;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDescriptionFinder {

    private final UserDescriptionRepository userDescriptionRepository;

    public Optional<UserDescription> find(UUID userId, UUID userDescriptionId) {
        return userDescriptionRepository.findById(userDescriptionId);
    }

}
