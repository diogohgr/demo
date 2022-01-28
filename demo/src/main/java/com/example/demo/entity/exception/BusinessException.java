package com.example.demo.entity.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String message){
        super(message);
    }
}
