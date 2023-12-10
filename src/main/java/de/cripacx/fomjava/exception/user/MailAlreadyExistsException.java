package de.cripacx.fomjava.exception.user;

import de.cripacx.fomjava.exception.FomException;
import org.springframework.http.HttpStatus;

public class MailAlreadyExistsException extends FomException {
    public MailAlreadyExistsException() {
        super("Mail already exists", HttpStatus.CONFLICT);
    }
}
