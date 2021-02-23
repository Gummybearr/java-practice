package com.ververica.demo.backend.utils;

import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.List;

public class ErrorChecker<T, E> {
  public void checkEmptyListThrowError(List<T> list, E exception) throws Throwable {
    if (list.isEmpty()) throw (Throwable) exception;
  }

  public void checkInputSizeFulfilled(int inputSize, int expectationSize, E exception) throws Throwable {
    if(inputSize!=expectationSize) throw (Throwable) exception;
  }
}
