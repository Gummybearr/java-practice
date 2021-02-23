package com.ververica.demo.backend.users.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class UserJpaException extends RuntimeException{

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DuplicateEmailException extends Throwable {
        private String email;
        private UserJpaErrorCode errorCode;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InvalidUserException extends Throwable {
        private String email;
        private UserJpaErrorCode errorCode;
    }

}
