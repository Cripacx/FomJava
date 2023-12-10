package de.cripacx.fomjava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class FomException extends Exception {

    private String message;
    private HttpStatus httpStatus;

    public FomException(String message, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ResponseEntity<String> toResponseEntity() {
        return new ResponseEntity<String>(this.message, this.httpStatus);
    }

}
