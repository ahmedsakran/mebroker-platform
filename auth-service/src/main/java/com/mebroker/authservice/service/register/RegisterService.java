package com.mebroker.authservice.service.register;

import com.mebroker.authservice.dto.request.RegisterRequest;

public interface RegisterService {

    void register(RegisterRequest request);
}