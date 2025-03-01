package com.williamsdreams.vl_learning.users.descriptions.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserDescriptionRepository {

    Optional<UserDescription> findById(UUID userId);

    UserDescription create(UserDescription userDescription);

}
