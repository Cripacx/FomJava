package de.cripacx.fomjava.controller;

import de.cripacx.fomjava.entity.User;
import de.cripacx.fomjava.exception.FomException;
import de.cripacx.fomjava.model.RecipeRequestModel;
import de.cripacx.fomjava.service.RecipeService;
import de.cripacx.fomjava.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private RecipeService recipeService;
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> getAll(@RequestHeader String auth) {
        try {
            return this.recipeService.getAll(this.userService.getUserByHeader(auth));
        } catch (FomException e) {
            return e.toResponseEntity();
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestHeader String auth, @RequestBody String body) {
        try {
            return this.recipeService.create(this.userService.getUserByHeader(auth), RecipeRequestModel.fromJson(body));
        } catch (FomException e) {
            return e.toResponseEntity();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestHeader String auth, @RequestParam UUID id) {
        try {
            return this.recipeService.delete(this.userService.getUserByHeader(auth), id);
        } catch (FomException e) {
            return e.toResponseEntity();
        }
    }

    @PutMapping
    public ResponseEntity<String> edit(@RequestHeader String auth, @RequestParam UUID id, @RequestBody String body) {
        try {
            return this.recipeService.edit(this.userService.getUserByHeader(auth), id, RecipeRequestModel.fromJson(body));
        } catch (FomException e) {
            return e.toResponseEntity();
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestHeader String auth, @RequestBody String base64) {
        try {
            return this.recipeService.uploadImage(this.userService.getUserByHeader(auth), base64);
        } catch (FomException e) {
            return e.toResponseEntity();
        }
    }

}
