package com.littlebirds.petshop.domain.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    CLIENT("ROLE_CLIENT"),
    WORKER("ROLE_WORKER");
    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
