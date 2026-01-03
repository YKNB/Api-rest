package com.quest.etna.dto;

import com.quest.etna.model.UserRole;

import java.time.LocalDateTime;
import java.util.List;

public class UsersResponseDTO {
    private int id;
    private String username;
    private String role;
    private LocalDateTime creationDate;
    private List<AddressResponseDTO> addressesList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<AddressResponseDTO> getAddressesList() {
        return addressesList;
    }

    public void setAddressesList(List<AddressResponseDTO> addressesList) {
        this.addressesList = addressesList;
    }
}
