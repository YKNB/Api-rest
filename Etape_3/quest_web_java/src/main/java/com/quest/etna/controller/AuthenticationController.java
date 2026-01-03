package com.quest.etna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.config.WorkerJob;
import com.quest.etna.config.cryPass;
import com.quest.etna.dto.UserResponseDTO;
import com.quest.etna.model.Erreur;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authentificationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private WorkerJob workerjob;
    private cryPass passwordEncoder;

    public AuthenticationController(UserRepository userRepository,WorkerJob workerjob,
                                    cryPass passwordEncoder) {
        this.userRepository = userRepository;
        this.workerjob = workerjob;
        this.passwordEncoder = passwordEncoder;
    }

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
            User u = new User(username, passwordEncoder.passwordEncoder().encode(user.getPassword()));
            userRepository.save(u);
            result.put("username", username);
            result.put("role", u.getRole().toString());
            return new ResponseEntity<HashMap<String, String>>(result , HttpStatus.CREATED);
        }
    }

    @PostMapping("/authenticate")
    ResponseEntity<?> authenticate(@RequestBody User mUser) {
        try {
            String username = mUser.getUsername();
            String password = mUser.getPassword();

            workerjob.initUser(mUser.getUsername(),
                    mUser.getPassword());
            // Initialize the user and get the token
            String mToken = workerjob.getValidToken();

            // Create a response JSON with the token
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("token", mToken);

            String jsonString = mapper.writeValueAsString(result);

            // Return the response
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jsonString);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Erreur("Mauvais utilisateur / mot de passe"));
        }
    }



    @GetMapping("/me")
    public ResponseEntity<?> me() {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> user = userRepository.findByUsername(userName);

            if (user.isPresent()) {
                UserResponseDTO userResponse = new UserResponseDTO();
                userResponse.setId(user.get().getId());
                userResponse.setUsername(user.get().getUsername());
                userResponse.setRole(user.get().getRole().toString());
                userResponse.setCreationDate(user.get().getcreation_date());

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(userResponse);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new Erreur("Utilisateur non trouvé"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Erreur("Erreur"));
        }
    }








}
