package com.mebroker.authservice.service;

import com.mebroker.authservice.entity.User;

public interface TokenService {
    String createAccessToken(User user);
    String createRefreshToken(User user);
}