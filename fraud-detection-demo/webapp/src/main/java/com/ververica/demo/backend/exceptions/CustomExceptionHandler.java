package com.ververica.demo.backend.exceptions;

import com.ververica.demo.backend.datasources.exception.DatasourceJpaException;
import com.ververica.demo.backend.topics.exception.TopicJpaException;
import com.ververica.demo.backend.userDatasources.exception.UserDatasourceJpaException;
import com.ververica.demo.backend.users.exception.UserJpaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

@ControllerAdvice
@ResponseBody
@Slf4j
public class CustomExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public final ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
    return buildError(ErrorCode.ENTITY_NOT_FOUND);
  }

  @ExceptionHandler(DatasourceJpaException.DatasourceNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public final ErrorResponse handleDatasourceNotFoundException(DatasourceJpaException.DatasourceNotFoundException e) {
    log.error(String.valueOf(e));
    return buildError(ErrorCode.ENTITY_NOT_FOUND);
  }

  @ExceptionHandler(InputMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public final ErrorResponse handleInputMismatchException(InputMismatchException e) {
    return buildError(ErrorCode.INVALID_INPUT_VALUE);
  }

  @ExceptionHandler(UserJpaException.DuplicateEmailException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public final ErrorResponse handleDuplicateEmailException(UserJpaException.DuplicateEmailException e){
    log.error(String.valueOf(e));
    return buildError(ErrorCode.DUPLICATE_KEY_FOUND);
  }

  @ExceptionHandler(UserJpaException.InvalidUserException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public final ErrorResponse handleInvalidUserException(UserJpaException.InvalidUserException e){
    log.error(String.valueOf(e));
    return buildError(ErrorCode.INVALID_USER);
  }

  @ExceptionHandler(UserDatasourceJpaException.DatasourceNotAllocatedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public final ErrorResponse handleDatasourceNotAllocatedException(UserDatasourceJpaException.DatasourceNotAllocatedException e){
    log.error(String.valueOf(e));
    return buildError(ErrorCode.DATA_NOT_FOUND);
  }

  @ExceptionHandler(TopicJpaException.TopicSizeMismatchException.class)
  public final ErrorResponse handleTopicSizeMismatchException(TopicJpaException.TopicSizeMismatchException e){
    log.error(String.valueOf(e));
    return buildError(ErrorCode.INPUT_SIZE_EXPECTATION_NOT_FULFILLED);
  }

  @ExceptionHandler(TopicJpaException.TopicNotFoundException.class)
  public final ErrorResponse handleTopicNotFoundException(TopicJpaException.TopicNotFoundException e){
    log.error(String.valueOf(e));
    return buildError(ErrorCode.DATA_NOT_FOUND);
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public final ErrorResponse handleNoSuchElementException(NoSuchElementException e){
    return buildError(ErrorCode.DATA_NOT_FOUND);
  }

  private ErrorResponse buildError(ErrorCode errorCode){
    return ErrorResponse.builder()
            .status(errorCode.getStatus())
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build();
  }
}
