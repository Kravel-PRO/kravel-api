package com.kravel.server.auth.model;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }
}