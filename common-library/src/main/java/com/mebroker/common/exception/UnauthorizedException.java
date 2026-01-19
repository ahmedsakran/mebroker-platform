package com.mebroker.common.exception;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        this("UNAUTHORIZED", message);
    }

    public UnauthorizedException(String code, String message) {
        super(code, message, 401);
    }
}
