package com.mebroker.common.exception;

public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        this("BAD_REQUEST", message);
    }

    public BadRequestException(String code, String message) {
        super(code, message, 400);
    }
}