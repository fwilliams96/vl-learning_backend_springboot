package com.williamsdreams.vl_learning.users.readings.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserReadingRepository {

    Optional<UserReading> findById(UUID id);

    UserReading save(UserReading userReading);
}
