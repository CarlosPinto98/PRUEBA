package com.unimag.aeropuerto.API.Error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
