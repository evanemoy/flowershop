package com.example.flowershop.core.exception;

public class NotEnoughFlowers extends RuntimeException {
  public NotEnoughFlowers(String message){
    super(message);
  }
}
