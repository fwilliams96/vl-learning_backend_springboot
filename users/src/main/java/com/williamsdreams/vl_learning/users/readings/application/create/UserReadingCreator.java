package com.williamsdreams.vl_learning.users.readings.application.create;

import com.williamsdreams.vl_learning.users.readings.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReadingCreator {

    private final UserReadingRepository userReadingRepository;
    private final UserReadingGeneratorGateway userReadingGeneratorGateway;

    public UserReading create(UUID userId, NewUserReading newUserReading) {
        Reading reading = userReadingGeneratorGateway.generate(newUserReading.getTopic());
        log.info("Generated reading: {}", reading);
        return userReadingRepository.save(
                UserReading.builder()
                        .id(UUID.randomUUID())
                        .title(reading.getTitle())
                        .topic(newUserReading.getTopic())
                        .text(reading.getText())
                        .questions(reading.getQuestions())
                        .userId(userId)
                        .eventId(newUserReading.getEventId())
                        .build()
        );

    }

}
