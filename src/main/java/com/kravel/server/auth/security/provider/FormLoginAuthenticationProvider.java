package com.kravel.server.auth.security.provider;

import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
import com.kravel.server.auth.security.token.PreAuthorizationToken;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final MemberQueryRepository memberQueryRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getPassword();

        Member member = memberQueryRepository.findMemberByLoginEmail(username).orElseThrow(() -> new BadCredentialsException("isNotExistMember"));
        if (isCorrectPassword(password, member)) {
            return PostAuthorizationToken.getTokenFromMemberContext(
                    MemberContext.fromMemberModel(member)
            );
        }

        throw new BadCredentialsException("isNotCorrectPassword");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, Member member) {
        return passwordEncoder.matches(password, member.getLoginPw());
    }
}
