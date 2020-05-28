package com.kravel.server.auth.security.provider;

import com.kravel.server.auth.mapper.AccountMapper;
import com.kravel.server.auth.model.Account;
import com.kravel.server.auth.model.AccountContext;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
import com.kravel.server.auth.security.token.PreAuthorizationToken;
import com.kravel.server.auth.service.AccountContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountContextService accountContextService;
    private AccountMapper accountMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getPassword();

        Account account = accountMapper.findByLoginEmail(username);
        if (account.getLoginEmail().isEmpty()) {
            throw new NoSuchElementException("Account is empty!");
        }

        if (isCorrectPassword(password, account)) {
            return PostAuthorizationToken.getTokenFromAccountContext(
                    AccountContext.fromAccountModel(account)
            );
        }

        throw new NoSuchElementException("is not correct password!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, Account account) {
        return passwordEncoder.matches(account.getLoginPw(), password);
    }
}
