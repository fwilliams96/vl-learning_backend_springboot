package com.williamsdreams.vl_learning.gateway_app.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouterValidator routerValidator;

    public static class Config {
        // Put configuration properties here
    }

    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            if (!routerValidator.isSecured.test(request)) {
                return chain.filter(exchange);
            }

            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String idToken = authHeader.substring(7); // Remove "Bearer "

            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                String userId = decodedToken.getUid();

                // Add userId as a header
                ServerWebExchange modifiedExchange = exchange.mutate().request(
                        exchange.getRequest().mutate().header("userId", userId).build()
                ).build();

                return chain.filter(modifiedExchange);
            } catch (Exception e) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return null;
    }

    private boolean authMissing(ServerHttpRequest httpRequest) {
        return !httpRequest.getHeaders().containsKey("Authorization");
    }

}
