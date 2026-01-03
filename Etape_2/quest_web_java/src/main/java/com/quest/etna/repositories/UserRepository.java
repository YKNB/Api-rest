package com.quest.etna.repositories;

import com.quest.etna.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username=:nom" )
    public Optional<User> findByUsername(String nom);
}
