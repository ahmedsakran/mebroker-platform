package com.mebroker.common.security;

import java.util.List;

public class JwtClaims {

    private String userId;
    private List<String> roles;

    public JwtClaims(String userId, List<String> roles) {
        this.userId = userId;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getRoles() {
        return roles;
    }
}