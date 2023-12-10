package de.cripacx.fomjava.util;

import de.cripacx.fomjava.FomJavaApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractResponse {
    public String toJson() {
        return FomJavaApplication.getGson().toJson(this);
    }

    public ResponseEntity<String> toResponseEntity(HttpStatus httpStatus) {
        return new ResponseEntity<String>(toJson(), httpStatus);
    }
}
