package com.ververica.demo.backend.exceptions;

import lombok.*;

@NoArgsConstructor
@ToString
@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    @Builder
    public ErrorResponse(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
