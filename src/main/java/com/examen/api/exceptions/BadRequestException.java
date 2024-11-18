package com.examen.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
  public BadRequestException() {
    super();
  }

  public BadRequestException(String s) {
    super(s);
  }

  public BadRequestException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public BadRequestException(Throwable throwable) {
    super(throwable);
  }
}
