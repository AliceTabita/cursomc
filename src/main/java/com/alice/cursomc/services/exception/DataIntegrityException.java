package com.alice.cursomc.services.exception;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String msg, Throwable cause){
        super(msg,cause);
    }
    public DataIntegrityException(String msg){
        super(msg);
    }

}

