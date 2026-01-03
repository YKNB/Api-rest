package com.quest.etna.dto;

import com.quest.etna.model.User;
import com.quest.etna.model.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class UserUpdateDTO {
    @Id
    private Long id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private UserRole role;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
