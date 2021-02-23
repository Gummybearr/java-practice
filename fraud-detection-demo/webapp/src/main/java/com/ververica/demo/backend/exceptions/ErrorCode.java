package com.ververica.demo.backend.exceptions;

import lombok.*;

@Getter
@ToString
public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "E001", "Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "E002", "Entity Not Found"),
    DATA_NOT_FOUND(400, "E003", "Data Not Found"),
    DUPLICATE_KEY_FOUND(400, "E004", "Duplicate Key Found"),
    METHOD_NOT_ALLOWED(405, "E005", "Method Not Allowed"),
    INVALID_USER(400, "E006", "Invalid User"),
    INPUT_SIZE_EXPECTATION_NOT_FULFILLED(400, "E007", "Invalid User");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
