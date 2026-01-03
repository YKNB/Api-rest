package com.quest.etna.dto;

public class GenericResponseDTO {
    private String message;
    private int id; // Ajoutez le champ id de type Long

    public GenericResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
