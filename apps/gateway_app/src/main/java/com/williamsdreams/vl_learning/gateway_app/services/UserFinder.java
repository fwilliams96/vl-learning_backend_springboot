package com.williamsdreams.vl_learning.gateway_app.services;

import com.williamsdreams.vl_learning.gateway_app.clients.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserFinder {

    private final WebClient webClient;

    public Mono<UserDto> findUser(String firebaseUserId) {
        return webClient.get()
                .uri("/api/v1/users/{firebaseUserId}", firebaseUserId)
                .retrieve()
                .bodyToMono(UserDto.class)
                .onErrorResume(e -> {
                    // Manejo de errores, puedes personalizar según tus necesidades
                    return Mono.error(new RuntimeException(String.format("Error finding user by external id: %s", firebaseUserId), e));
                });
    }

}
