package de.cripacx.fomjava.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends FomException{
    public BadRequestException() {
        super("Bad Request", HttpStatus.BAD_REQUEST);
    }
}
