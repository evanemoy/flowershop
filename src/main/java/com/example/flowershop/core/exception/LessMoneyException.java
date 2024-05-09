package com.example.flowershop.core.exception;

public class LessMoneyException extends RuntimeException{
  public LessMoneyException(String message){
    super(message);
  }
}
