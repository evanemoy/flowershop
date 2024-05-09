package com.example.flowershop.core.exception;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(String message){
    super(message);
  }
}
