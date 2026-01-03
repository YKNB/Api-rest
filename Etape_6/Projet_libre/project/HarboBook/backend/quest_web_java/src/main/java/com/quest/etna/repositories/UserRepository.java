package com.quest.etna.repositories;

import com.quest.etna.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username=:nom" )
    public Optional<User> findByUsername(String nom);

    @Query ("SELECT u FROM User u ORDER BY u.id ASC" )
    public List<User> getListByPage(Pageable pageable);

    List<User> findAll();






}
