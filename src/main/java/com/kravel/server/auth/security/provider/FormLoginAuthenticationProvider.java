package com.kravel.server.auth.security.provider;

import com.kravel.server.auth.mapper.AuthMapper;
import com.kravel.server.auth.model.Member;
import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
import com.kravel.server.auth.security.token.PreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final AuthMapper authMapper;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getPassword();

        Member member = authMapper.findByLoginEmail(username);
        if (member.getLoginEmail().isEmpty()) {
            throw new NoSuchElementException("Member is empty!");
        }

        System.out.println("여긴 바깥!");
        if (isCorrectPassword(password, member)) {
            return PostAuthorizationToken.getTokenFromMemberContext(
                    MemberContext.fromMemberModel(member)
            );
        }

        throw new NoSuchElementException("is not correct password!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, Member member) {
        return passwordEncoder.matches(password, member.getLoginPw());
    }
}
