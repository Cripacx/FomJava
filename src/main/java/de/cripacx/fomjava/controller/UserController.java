package de.cripacx.fomjava.controller;

import de.cripacx.fomjava.exception.FomException;
import de.cripacx.fomjava.model.UserRequestModel;
import de.cripacx.fomjava.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody String body) {
        try {
            return this.userService.createUser(UserRequestModel.fromJson(body));
        } catch (FomException e) {
            return e.toResponseEntity();
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestHeader String auth) {
        try {
            return this.userService.deleteUser(this.userService.getUserByHeader(auth));
        } catch (FomException e) {
            return e.toResponseEntity();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String body) {
        try {
            return this.userService.login(UserRequestModel.fromJson(body));
        } catch (FomException e) {
            return e.toResponseEntity();
        }
    }
}
