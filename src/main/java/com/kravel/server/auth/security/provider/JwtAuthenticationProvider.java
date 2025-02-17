package com.kravel.server.auth.security.provider;

import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.security.token.JwtPreProcessingToken;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
import com.kravel.server.auth.security.util.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();

        MemberContext context = jwtDecoder.decodedJwt(token);

        return PostAuthorizationToken.getTokenFromMemberContext(context);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
