package com.naturecode.trillion.exception;

public class IPException extends Exception {
  private static final long serialVersionUID = 1L;
  private String message;

  public IPException(String errorMessage) {
    this.message = errorMessage;
  }

  @Override
  public String getMessage(){
    return this.message;
  }
}
