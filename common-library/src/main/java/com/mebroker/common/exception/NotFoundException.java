package com.mebroker.common.exception;

public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        this("NOT_FOUND", message);
    }

    public NotFoundException(String code, String message) {
        super(code, message, 404);
    }
}