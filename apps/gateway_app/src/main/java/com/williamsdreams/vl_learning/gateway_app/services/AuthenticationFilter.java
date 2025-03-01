package com.williamsdreams.vl_learning.gateway_app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamsdreams.vl_learning.auth.application.check_expiration_date.JwtExpirationDateChecker;
import com.williamsdreams.vl_learning.auth.application.extract_user_id.JwtUserIdExtractor;
import com.williamsdreams.vl_learning.auth.domain.JWToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouterValidator routerValidator;
    private final ObjectMapper objectMapper;
    private final JwtUserIdExtractor jwtUserIdExtractor;
    private final JwtExpirationDateChecker jwtExpirationDateChecker;

    public static class Config {
        // Aquí puedes agregar propiedades de configuración si lo deseas.
    }

    public AuthenticationFilter(RouterValidator routerValidator,
                                ObjectMapper objectMapper,
                                JwtUserIdExtractor jwtUserIdExtractor,
                                JwtExpirationDateChecker jwtExpirationDateChecker) {
        super(Config.class);
        this.routerValidator = routerValidator;
        this.objectMapper = objectMapper;
        this.jwtUserIdExtractor = jwtUserIdExtractor;
        this.jwtExpirationDateChecker = jwtExpirationDateChecker;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (request.getMethod() == HttpMethod.OPTIONS) {
                return chain.filter(exchange);
            }

            if (!routerValidator.isSecured.test(request)) {
                return chain.filter(exchange);
            }

            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Authorization header is missing");
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Authorization header is invalid");
            }

            String token = authHeader.substring(7); // Elimina "Bearer "

            JWToken jwToken = JWToken.builder()
                    .accessToken(token)
                    .build();

            boolean expired = jwtExpirationDateChecker.isExpired(jwToken);
            if (expired) {
                log.error("Token has expired");
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Token has expired");
            }

            UUID userId = null;
            try {
                userId = jwtUserIdExtractor.extract(jwToken);
            } catch (Exception e) {
                log.error("Error while extracting user ID from token", e);
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Invalid token");
            }

            // Con el userId extraído, se añade el header "X-User-Id" y se continúa con la cadena
            return Mono.just(userId)
                    .flatMap(uid -> {
                        ServerWebExchange modifiedExchange = exchange.mutate().request(
                                request.mutate().header("X-User-Id", uid.toString()).build()
                        ).build();
                        return chain.filter(modifiedExchange);
                    })
                    .onErrorResume(e -> {
                        log.error("Error while processing token", e);
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
