package com.kravel.server.auth.security.token;

import com.kravel.server.auth.model.MemberContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public MemberContext getMemberContext() {
        return (MemberContext) super.getPrincipal();
    }

    public static PostAuthorizationToken getTokenFromMemberContext(MemberContext context) {
        return new PostAuthorizationToken(context, context.getPassword(), context.getAuthorities());
    }
}
