package com.kravel.server.auth.model;

import com.kravel.server.auth.security.token.JwtPostProcessingToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MemberContext extends User {

    private Member member;

    private MemberContext(Member member, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.member = member;
    }

    public MemberContext(String loginEmail, String loginPw, String role) {
        super(loginEmail, loginPw, parseAuthorities(role));
    }

    public static MemberContext fromMemberModel(Member member) {
        return new MemberContext(member, member.getLoginEmail(), member.getLoginPw(), parseAuthorities(member.getRole()));
    }

    public static MemberContext fromJwtPostToken(JwtPostProcessingToken token) {
        return new MemberContext(null, token.getLoginEmail(), token.getLoginPw(), token.getAuthorities());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(Role role) {
        return Arrays.asList(role).stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(String role) {
        return parseAuthorities(Role.getRoleByName(role));
    }

    public Member getMember() {
        return member;
    }

}
