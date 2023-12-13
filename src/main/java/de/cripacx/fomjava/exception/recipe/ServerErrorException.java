package de.cripacx.fomjava.exception.recipe;

import de.cripacx.fomjava.exception.FomException;
import org.springframework.http.HttpStatus;

public class ServerErrorException extends FomException {
    public ServerErrorException() {
        super("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
