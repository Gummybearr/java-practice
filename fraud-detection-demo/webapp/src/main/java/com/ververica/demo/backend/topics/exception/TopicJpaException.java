package com.ververica.demo.backend.topics.exception;

import com.ververica.demo.backend.users.domain.User;
import com.ververica.demo.backend.users.exception.UserJpaErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class TopicJpaException {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopicSizeMismatchException extends Throwable {
        private String email;
        private TopicJpaErrorCode errorCode;

        public TopicSizeMismatchException(User user, TopicJpaErrorCode topicJpaErrorCode){
            this.email = user.getEmail();
            this.errorCode = topicJpaErrorCode;
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopicNotFoundException extends Throwable {
        private String email;
        private String topicName;
        private TopicJpaErrorCode errorCode;

        public TopicNotFoundException(User user, String topicName, TopicJpaErrorCode topicJpaErrorCode){
            this.email = user.getEmail();
            this.topicName = topicName;
            this.errorCode = topicJpaErrorCode;
        }
    }

}
