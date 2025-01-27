package com.williamsdreams.vl_learning.gateway_app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouterValidator routerValidator;
    private final ObjectMapper objectMapper;

    public static class Config {
        // Put configuration properties here
    }

    public AuthenticationFilter(RouterValidator routerValidator, ObjectMapper objectMapper) {
        super(Config.class);
        this.routerValidator = routerValidator;
        this.objectMapper = objectMapper;
    }

    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            if (!routerValidator.isSecured.test(request)) {
                return chain.filter(exchange);
            }

            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Authorization header is missing");
            }
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Authorization header is invalid");
            }

            String idToken = authHeader.substring(7); // Remove "Bearer "

            return Mono.fromCallable(() -> FirebaseAuth.getInstance().verifyIdToken(idToken))
                .subscribeOn(Schedulers.boundedElastic())
                .map(FirebaseToken::getUid)
                .flatMap(externalUserId -> {
                    ServerWebExchange modifiedExchange = exchange.mutate().request(
                            exchange.getRequest().mutate().header("X-User-Id", externalUserId).build()
                    ).build();
                    return chain.filter(modifiedExchange);
                })
                .onErrorResume(e -> {
                    log.error("Error while authenticating", e);
                    return onError(exchange, HttpStatus.UNAUTHORIZED, "Invalid token");
                });
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException e) {
            bytes = ("{\"message\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
        }

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    private boolean authMissing(ServerHttpRequest httpRequest) {
        return !httpRequest.getHeaders().containsKey("Authorization");
    }

}
