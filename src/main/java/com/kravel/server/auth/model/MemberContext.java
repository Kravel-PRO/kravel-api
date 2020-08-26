package com.kravel.server.auth.model;

import com.kravel.server.auth.security.token.JwtPostProcessingToken;
import com.kravel.server.model.member.Member;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class MemberContext extends User {

    private Member member;

    private MemberContext(Member member, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.member = member;
    }

    public MemberContext(String loginEmail, String loginPw, String roleType) {
        super(loginEmail, loginPw, parseAuthorities(roleType));
    }

    public static MemberContext fromMemberModel(Member member) {
        return new MemberContext(member, member.getLoginEmail(), member.getLoginPw(), parseAuthorities(member.getRoleType()));
    }

    public static MemberContext fromJwtPostToken(JwtPostProcessingToken token) {
        return new MemberContext(null, token.getLoginEmail(), token.getLoginPw(), token.getAuthorities());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(RoleType roleType) {
        return Arrays.asList(roleType).stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(String roleType) {
        return parseAuthorities(RoleType.getRoleByName(roleType));
    }

    public Member getMember() {
        return member;
    }

}
