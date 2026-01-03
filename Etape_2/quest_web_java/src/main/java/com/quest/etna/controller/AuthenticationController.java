package com.quest.etna.controller;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @PostMapping(value="/register")
    public ResponseEntity<HashMap<String, String>> enregister(@RequestBody User user) {

        HashMap<String, String> result = new HashMap<String, String>();
        String username = user.getUsername();

        //Bad request
        if (username == null || user.getPassword() == null) {
            result.put("status","requête invalide");
            return new ResponseEntity<HashMap<String, String>>(result, HttpStatus.BAD_REQUEST);
        }

        //The username already exist
        else if (!userRepository.findByUsername(username).isEmpty()) {
            result.put("status","username déjà utilisé");
            return new ResponseEntity<HashMap<String, String>>(result, HttpStatus.CONFLICT);
        }
        //create a new user
        else {
            User u = new User(username,user.getPassword());
            userRepository.save(u);
            result.put("username", username);
            result.put("role", u.getRole().toString());
            return new ResponseEntity<HashMap<String, String>>(result , HttpStatus.CREATED);
        }
    }
}
