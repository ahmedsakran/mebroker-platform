package com.mebroker.common.exception;

public class ForbiddenException extends BaseException {

    public ForbiddenException(String message) {
        this("FORBIDDEN", message);
    }

    public ForbiddenException(String code, String message) {
        super(code, message, 403);
    }
}