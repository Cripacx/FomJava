package de.cripacx.fomjava.exception.recipe;

import de.cripacx.fomjava.exception.FomException;
import org.springframework.http.HttpStatus;

public class RecipeNotFoundException extends FomException {
    public RecipeNotFoundException() {
        super("Recipe not found", HttpStatus.NOT_FOUND);
    }
}
