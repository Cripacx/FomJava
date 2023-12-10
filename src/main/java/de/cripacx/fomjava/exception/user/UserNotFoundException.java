package de.cripacx.fomjava.exception.user;

import de.cripacx.fomjava.exception.FomException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends FomException {
    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
