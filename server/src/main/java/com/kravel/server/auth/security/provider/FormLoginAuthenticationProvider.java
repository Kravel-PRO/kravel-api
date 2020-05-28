package com.kravel.server.auth.security.provider;

        import com.kravel.server.auth.model.AccountContext;
        import com.kravel.server.auth.security.token.PreAuthorizationToken;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.AuthenticationProvider;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.AuthenticationException;

public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountContext accountContext;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
