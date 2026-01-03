package com.quest.etna.model;

public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String toString() {
        switch (this) {
            case ROLE_USER:
                return "USER";
            case ROLE_ADMIN:
                return "ADMIN";
            default:
                return "NO ROLE";
        }
    }
}
