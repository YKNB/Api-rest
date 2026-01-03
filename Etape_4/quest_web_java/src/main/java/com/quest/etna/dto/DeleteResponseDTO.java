package com.quest.etna.dto;

public class DeleteResponseDTO {
    private boolean deleted;

    public DeleteResponseDTO(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
