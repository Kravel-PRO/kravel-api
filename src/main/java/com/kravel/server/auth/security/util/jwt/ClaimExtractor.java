package com.kravel.server.auth.security.util.jwt;

import com.kravel.server.auth.security.token.PostAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ClaimExtractor {

    public String getLoginEmail(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        Member member = token.getMemberContext().getMember();

        return member.getLoginEmail();
    }

    public String getSpeech(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        Member member = token.getMemberContext().getMember();

        return member.getSpeech();
    }

    public String getGender(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        Member member = token.getMemberContext().getMember();

        return member.getGender();
    }

    public long getMemberId(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        Member member = token.getMemberContext().getMember();

        return member.getMemberId();
    }
}
