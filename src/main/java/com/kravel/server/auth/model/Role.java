package com.kravel.server.auth.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum Role {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCorrectName(String name) {
        return name.equalsIgnoreCase(this.roleName);
    }

    public static Role getRoleByName(String roleName) {
        return Arrays.stream(Role.values()).filter(r -> r.isCorrectName(roleName)).findFirst().orElseThrow(() -> new NoSuchElementException("No have ROLE"));
    }
}
