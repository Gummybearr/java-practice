package com.ververica.demo.backend.druid.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

public class DruidDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttributeReq{
        @NotNull
        private String query;
    }
}
