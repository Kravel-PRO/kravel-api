package com.kravel.server.auth.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {

    public JwtPreProcessingToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtPreProcessingToken(String token) {
        super(token, token.length());
    }
}
