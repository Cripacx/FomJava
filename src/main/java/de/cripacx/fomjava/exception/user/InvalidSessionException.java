package de.cripacx.fomjava.exception.user;

import de.cripacx.fomjava.exception.FomException;
import org.springframework.http.HttpStatus;

public class InvalidSessionException extends FomException {
    public InvalidSessionException() {
        super("Not authorized", HttpStatus.FORBIDDEN);
    }
}
