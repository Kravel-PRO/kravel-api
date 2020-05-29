package com.kravel.server.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

public class TokenDTO {

    @JsonProperty("token")
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
}
