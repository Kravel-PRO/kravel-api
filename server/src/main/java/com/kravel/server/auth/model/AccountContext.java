package com.kravel.server.auth.model;

import com.kravel.server.auth.security.token.JwtPostProcessingToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountContext extends User {

    private Account account;

    private AccountContext(Account account, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.account = account;
    }

    public AccountContext(String loginEmail, String loginPw, String role) {
        super(loginEmail, loginPw, parseAuthorities(role));
    }

    public static AccountContext fromAccountModel(Account account) {
        return new AccountContext(account, account.getLoginEmail(), account.getLoginPw(), parseAuthorities(account.getUserRole()));
    }

    public static AccountContext fromJwtPostToken(JwtPostProcessingToken token) {
        return new AccountContext(null, token.getLoginEmail(), token.getLoginPw(), token.getAuthorities());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(UserRole role) {
        return Arrays.asList(role).stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(String role) {
        return parseAuthorities(UserRole.getRoleByName(role));
    }

    public Account getAccount() {
        return account;
    }

}
