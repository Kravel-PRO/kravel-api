package com.kravel.server.auth.security.util.jwt;

import com.kravel.server.auth.model.Member;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClaimExtractor {

    public String getLoginEmail(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        Member member = token.getMemberContext().getMember();

        return member.getLoginEmail();
    }

    public String getLanguage(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        Member member = token.getMemberContext().getMember();

        return member.getLanguage();
    }

    public String getGender(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        Member member = token.getMemberContext().getMember();

        return member.getGender();
    }
}
