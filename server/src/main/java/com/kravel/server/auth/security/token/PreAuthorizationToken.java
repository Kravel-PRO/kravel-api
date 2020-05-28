package com.kravel.server.auth.security.token;

import com.kravel.server.auth.dto.FormLoginDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    public PreAuthorizationToken(FormLoginDTO dto) {
        this(dto.getLoginEmail(), dto.getLoginPw());
    }

    public String getUsername() {
        return (String) super.getPrincipal();
    }

    public String getPassword() {
        return (String) super.getCredentials();
    }

}
