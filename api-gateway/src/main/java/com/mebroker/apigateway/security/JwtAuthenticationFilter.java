package com.mebroker.apigateway.security;

import com.mebroker.common.security.JwtClaims;
import com.mebroker.common.security.JwtConstants;
import com.mebroker.common.security.JwtValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtValidator jwtValidator;

    public JwtAuthenticationFilter(
            @Value("${security.jwt.secret}") String jwtSecret) {
        this.jwtValidator = new JwtValidator(jwtSecret);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // Allow auth endpoints without token
        if (exchange.getRequest().getURI().getPath().startsWith("/auth")) {
            return chain.filter(exchange);
        }

        if (authHeader == null || !authHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            JwtClaims claims = jwtValidator.validateToken(authHeader);

            // Pass user info to downstream services via headers
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(builder -> builder
                            .header("X-User-Id", claims.getUserId())
                            .header("X-Roles", String.join(",", claims.getRoles())))
                    .build();

            return chain.filter(modifiedExchange);

        } catch (Exception ex) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1; // run early
    }
}