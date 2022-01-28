package com.example.demo.entity.exception;

public class InvalidTransferException extends BusinessException{

    public InvalidTransferException(String message){
        super(message);
    }
}
