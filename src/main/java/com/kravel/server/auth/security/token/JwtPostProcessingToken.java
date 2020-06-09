package com.kravel.server.auth.security.token;

import com.kravel.server.auth.model.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtPostProcessingToken extends UsernamePasswordAuthenticationToken {

    private JwtPostProcessingToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public JwtPostProcessingToken(String loginEmail, Role role) {
        super(loginEmail, "UNUSED_CREDENTIALS", parseAuthorities(role));
    }

    private static Collection<? extends GrantedAuthority> parseAuthorities(Role role) {
        return Arrays.asList(role).stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    public String getLoginEmail() {
        return (String) super.getPrincipal();
    }

    public String getLoginPw() {
        return (String) super.getCredentials();
    }
}