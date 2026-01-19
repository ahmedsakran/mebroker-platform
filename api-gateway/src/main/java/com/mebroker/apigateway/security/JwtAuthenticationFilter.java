package com.mebroker.apigateway.security;

import com.mebroker.common.security.JwtClaims;
import com.mebroker.common.security.JwtConstants;
import com.mebroker.common.security.JwtValidator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtValidator jwtValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // ✅ Allow auth & actuator endpoints
        if (path.startsWith("/auth") || path.startsWith("/actuator")) {
            return chain.filter(exchange);
        }

        // ✅ Allow CORS preflight requests
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(JwtConstants.TOKEN_PREFIX.length());

        try {
            Claims claims = jwtValidator.validateAndGetClaims(token);

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(
                            exchange.getRequest().mutate()
                                    .header(JwtClaims.USER_ID, claims.get(JwtClaims.USER_ID).toString())
                                    .header(JwtClaims.USERNAME, claims.getSubject())
                                    .header(
                                            JwtClaims.ROLES,
                                            claims.get(JwtClaims.ROLES).toString()
                                    )
                                    .build()
                    )
                    .build();

            return chain.filter(mutatedExchange);

        } catch (Exception ex) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1; // run before other filters
    }
}
