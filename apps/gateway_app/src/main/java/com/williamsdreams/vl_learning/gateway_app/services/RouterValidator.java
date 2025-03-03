package com.williamsdreams.vl_learning.gateway_app.services;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

    public static final List<String> OPEN_ENDPOINTS = List.of();

    public Predicate<ServerHttpRequest> isSecured = serverHttpRequest ->
            OPEN_ENDPOINTS.stream().noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));



}
