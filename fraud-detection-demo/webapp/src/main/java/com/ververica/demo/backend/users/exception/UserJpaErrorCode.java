package com.ververica.demo.backend.users.exception;

import lombok.Getter;

@Getter
public enum UserJpaErrorCode {
    DUPLICATE_EMAIL_FOUND,
    INVALID_USER
}
