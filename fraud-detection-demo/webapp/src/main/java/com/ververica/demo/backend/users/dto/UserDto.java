package com.ververica.demo.backend.users.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

public class UserDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserReq {
        @NotNull
        private String email;
        @NotNull
        private String name;
        @NotNull
        private String password;
    }
}
