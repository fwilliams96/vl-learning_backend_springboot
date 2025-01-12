package com.williamsdreams.vl_learning.auth.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID userId);

    Optional<User> findByEmail(String email);

    User create(User user);

}
