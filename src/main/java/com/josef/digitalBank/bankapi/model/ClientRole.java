package com.josef.digitalBank.bankapi.model;

public enum ClientRole {

    ADMIN("admin"),
    CLIENT("client");

    private String role;

    ClientRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
