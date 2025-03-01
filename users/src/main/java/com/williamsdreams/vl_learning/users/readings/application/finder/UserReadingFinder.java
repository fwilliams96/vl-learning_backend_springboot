package com.williamsdreams.vl_learning.users.readings.application.finder;

import com.williamsdreams.vl_learning.users.readings.domain.UserReading;
import com.williamsdreams.vl_learning.users.readings.domain.UserReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserReadingFinder {

    private final UserReadingRepository userReadingRepository;

    public Optional<UserReading> findById(UUID id) {
        return userReadingRepository.findById(id);
    }

}
