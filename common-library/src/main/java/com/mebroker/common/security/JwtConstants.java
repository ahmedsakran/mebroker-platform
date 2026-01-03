package com.mebroker.common.security;

public final class JwtConstants {

    private JwtConstants() {
        // prevent instantiation
    }

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_ROLES = "roles";
}