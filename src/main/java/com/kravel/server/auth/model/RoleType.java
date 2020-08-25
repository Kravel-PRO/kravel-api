package com.kravel.server.auth.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum RoleType {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCorrectName(String name) {
        return name.equalsIgnoreCase(this.roleName);
    }

    public static RoleType getRoleByName(String roleName) {
        return Arrays.stream(RoleType.values()).filter(r -> r.isCorrectName(roleName)).findFirst().orElseThrow(() -> new NoSuchElementException("No have ROLE"));
    }
}
