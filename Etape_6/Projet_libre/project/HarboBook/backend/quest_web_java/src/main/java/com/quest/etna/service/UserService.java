package com.quest.etna.service;

import com.quest.etna.model.User;
import com.quest.etna.model.UserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements ModelService<User> {

    @Autowired
    UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getList(Integer page, Integer limit) {
        PageRequest pageable = PageRequest.of(page, limit);
        return userRepository.getListByPage(pageable);
    }

    @Override
    public User getOneByName(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        if (user.isEmpty()) return null;
        return user.get();
    }

    @Override
    public User createEntity(User entity) {
        userRepository.save(entity);
        return entity;
    }

    @Override
    public User update(String username, User entity) {
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println(user.get());
        if (user.isEmpty()) return null;

        User userFound = user.get();
        userFound.setUsername(entity.getUsername());
        userFound.setPassword(entity.getPassword());
        userFound.setRole(entity.getRole());
        userFound.update_date();;
        userRepository.save(userFound);
        return userFound;
    }


    public Boolean delete(String username) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) return null;
            User userFound = user.get();
            userRepository.delete(userFound);

        }catch (Exception e) {
            return false;
        }
        return true;
    }

  //  @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> checkAllUsers() {
        List<User> listUser = this.userRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listUser);
    }
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> checkUserById(User mUser) {
        Optional<User> user = this.userRepository.findByUsername(mUser.getUsername());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user.get());
    }

 //  @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(int userId, User upUser, User oldUser) {
        // Vérifiez si l'utilisateur actuel a l'autorisation de mettre à jour un utilisateur

        // Obtenez l'utilisateur actuel
        UserDetails userDetails = (UserDetails) SecurityContextHolder.
                getContext().getAuthentication()
                .getPrincipal();
        String currentUsername = userDetails.getUsername();

        if (upUser.getRole() != null &&  upUser.getRole() ==  UserRole.ROLE_ADMIN) {
            oldUser.setRole(upUser.getRole());
        }

        if (currentUsername.equals(oldUser.getUsername()) ) {
            // L'utilisateur actuel a le droit de mettre à jour son propre profil
            if (upUser.getUsername() != null) {
                oldUser.setUsername(upUser.getUsername());
            }
            // Mettez à jour l'utilisateur ici

            // Renvoyez une réponse réussie
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(oldUser);
        } else {
            // L'utilisateur actuel n'a pas l'autorisation de mettre à jour cet utilisateur
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("{ \"error\": \"User Role UNAUTHORIZED\" }");
        }
    }


}
