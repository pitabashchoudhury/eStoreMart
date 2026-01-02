package org.ecom.apigateway.config;


import lombok.extern.slf4j.Slf4j;
import org.ecom.apigateway.util.JwtUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
//@Order(-1)
@Slf4j
public class JwtValidationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;
    private static final List<String> PUBLIC_URLS = List.of(
            "/auth/users/login-user",
            "/auth/users/create-user"
    );
    @Autowired
    public JwtValidationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        boolean isPublic =
                PUBLIC_URLS.stream().anyMatch(path::startsWith);

        if (isPublic) {
            return chain.filter(exchange);
        }

        String authHeader =
                exchange.getRequest().getHeaders()
                        .getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }


}
