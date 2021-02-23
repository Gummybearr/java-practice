package com.ververica.demo.backend.userDatasources.exception;

import com.ververica.demo.backend.users.domain.User;
import com.ververica.demo.backend.users.exception.UserJpaErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class UserDatasourceJpaException {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DatasourceNotAllocatedException extends Throwable {
        private String email;
        private Long datasourceId;
        private UserDatasourceJpaErrorCode errorCode;

        public DatasourceNotAllocatedException(User user, Long datasourceId, UserDatasourceJpaErrorCode errorCode) {
            this.email = user.getEmail();
            this.datasourceId = datasourceId;
            this.errorCode = errorCode;
        }
    }
}
