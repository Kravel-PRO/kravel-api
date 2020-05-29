package com.kravel.server.auth.dto;

import org.springframework.beans.factory.annotation.Value;

public class TokenDTO {

    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
}
