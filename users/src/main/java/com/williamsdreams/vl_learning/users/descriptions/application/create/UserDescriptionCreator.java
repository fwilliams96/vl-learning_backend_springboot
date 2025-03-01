package com.williamsdreams.vl_learning.users.descriptions.application.create;

import com.williamsdreams.vl_learning.shared.domain.Image;
import com.williamsdreams.vl_learning.users.descriptions.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDescriptionCreator {

    private final UserDescriptionRepository userDescriptionRepository;
    private final UserDescriptionImageGateway userDescriptionImageGateway;

    public UserDescription create(UUID userId, NewUserDescription userDescription) {
        Image userDescriptionImage = userDescriptionImageGateway.generateImage(userDescription.getTopic());
        // TODO Generate title
        return userDescriptionRepository.create(
                UserDescription.builder()
                        .id(UUID.randomUUID())
                        .title(userDescription.getTopic())
                        .topic(userDescription.getTopic())
                        .eventId(userDescription.getEventId())
                        .userId(userId)
                        .image(userDescriptionImage)
                        .build()
        );
    }

}
