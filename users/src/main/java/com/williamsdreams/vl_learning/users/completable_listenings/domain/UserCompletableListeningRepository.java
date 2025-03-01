package com.williamsdreams.vl_learning.users.completable_listenings.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserCompletableListeningRepository {

    Optional<UserCompletableListening> findById(UUID id);

    UserCompletableListening create(UserCompletableListening userCompletableListening);

}
