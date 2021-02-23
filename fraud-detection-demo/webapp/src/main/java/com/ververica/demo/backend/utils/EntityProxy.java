package com.ververica.demo.backend.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public class EntityProxy<T, E> {
  public T entityCheckThrowException(List<T> entityList) {
    if (entityList.isEmpty()) throw new EntityNotFoundException(entityList.getClass().toString());
    return entityList.get(0);
  }

  public T entityCheckThrowException(List<T> entityList, E exception) throws Throwable {
    if (entityList.isEmpty()) throw (Throwable) exception;
    return entityList.get(0);
  }

  public T entityCheckThrowException(Optional<T> entity, E exception) throws Throwable {
    if (!entity.isPresent()) throw (Throwable) exception;
    return entity.get();
  }

  public T entityCheckThrowException(Optional<T> entity) {
    if (!entity.isPresent()) throw new EntityNotFoundException(entity.getClass().toString());
    return entity.get();
  }

  public T entityCheckReturnNullOnError(List<T> entityList) {
    return entityList.isEmpty() ? null : entityList.get(0);
  }

}
