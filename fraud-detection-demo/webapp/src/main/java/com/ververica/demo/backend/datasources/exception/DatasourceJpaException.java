package com.ververica.demo.backend.datasources.exception;

import com.ververica.demo.backend.users.exception.UserJpaErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class DatasourceJpaException {
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DatasourceNotFoundException extends Throwable {
        private String email;
        private Long datasourceId;
        private DatasourceJpaErrorCode errorCode;
    }

}
