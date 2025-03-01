package com.williamsdreams.vl_learning.users.listenings.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserListeningRepository {

    Optional<UserListening> findById(UUID id);

    UserListening create(UserListening userListening);

}
