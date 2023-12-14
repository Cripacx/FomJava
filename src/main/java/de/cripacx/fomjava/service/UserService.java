package de.cripacx.fomjava.service;


import de.cripacx.fomjava.entity.User;
import de.cripacx.fomjava.exception.FomException;
import de.cripacx.fomjava.exception.user.InvalidSessionException;
import de.cripacx.fomjava.exception.user.MailAlreadyExistsException;
import de.cripacx.fomjava.exception.user.UserNotFoundException;
import de.cripacx.fomjava.model.UserRequestModel;
import de.cripacx.fomjava.model.UserResponseModel;
import de.cripacx.fomjava.permission.Permission;
import de.cripacx.fomjava.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private UUID getFreeUserUUID(){
        UUID uuid = UUID.randomUUID();
        while(this.userRepository.existsById(uuid))
            uuid = UUID.randomUUID();
        return uuid;
    }

    public final User getUserByHeader(final String auth) throws FomException {
        if(!auth.contains(":"))
            throw new InvalidSessionException();
        final String[] data = auth.split(":");
        UUID uuid;
        try {
            uuid = UUID.fromString(data[0]);
        }catch (Exception e){
            throw new InvalidSessionException();
        }
        final User user = this.getById(uuid);
        if(!user.getSessionToken().equals(data[1]))
            throw new InvalidSessionException();
        return user;
    }

    public User getById(UUID id) throws UserNotFoundException {
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public ResponseEntity<String> createUser(UserRequestModel userRequestModel) throws FomException {

        if(userRepository.findByMailEquals(userRequestModel.getMail()).isPresent()) {
            throw new MailAlreadyExistsException();
        }
        this.userRepository.save(new User(this.getFreeUserUUID(),userRequestModel.getName(), userRequestModel.getMail(), userRequestModel.getPassword()));
        return new ResponseEntity<String>("Created", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteUser(User user) {
        this.userRepository.delete(user);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    public ResponseEntity<String> updatePassword(User user, String password) {
        user.setPassword(password);
        this.userRepository.save(user);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }


    public ResponseEntity<String> login(UserRequestModel userRequestModel) throws FomException {
        User user = this.userRepository.findByMailEquals(userRequestModel.getMail()).orElseThrow(UserNotFoundException::new);
        if(!user.getPassword().equals(userRequestModel.getPassword())) {
            throw new UserNotFoundException();
        }
        user.generateSessionToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("auth", user.getAuthHeader());
        this.userRepository.save(user);
        return new ResponseEntity<>(UserResponseModel.fromUser(user).toJson(), headers, HttpStatus.OK);
    }
}
