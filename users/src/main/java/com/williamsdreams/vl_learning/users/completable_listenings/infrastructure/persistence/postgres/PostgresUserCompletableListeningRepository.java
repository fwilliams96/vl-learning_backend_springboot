package com.williamsdreams.vl_learning.users.completable_listenings.infrastructure.persistence.postgres;

import com.williamsdreams.vl_learning.users.completable_listenings.domain.UserCompletableListening;
import com.williamsdreams.vl_learning.users.completable_listenings.domain.UserCompletableListeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostgresUserCompletableListeningRepository implements UserCompletableListeningRepository {

    @Override
    public Optional<UserCompletableListening> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public UserCompletableListening create(UserCompletableListening userCompletableListening) {
        // TODO: Implement
        return userCompletableListening;
    }
}
