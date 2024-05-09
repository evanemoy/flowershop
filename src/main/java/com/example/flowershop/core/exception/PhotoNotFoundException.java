package com.example.flowershop.core.exception;

public class PhotoNotFoundException extends RuntimeException{
  public PhotoNotFoundException(String message){
    super(message);
  }
}
