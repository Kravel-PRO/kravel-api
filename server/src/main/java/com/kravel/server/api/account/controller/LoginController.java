package com.kravel.server.api.account.controller;

import com.kravel.server.auth.model.Account;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class LoginController {

    @PostMapping("/sign-up")
    public Account signUp(@ModelAttribute Account account) {
        return new Account();
    }

    @PostMapping("/sign-in")
    public Account signIn(@ModelAttribute Account account) {
        return  new Account();
    }

    // EXAMPLE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Account modifyAccount(@PathVariable("id") int id, Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;

        return new Account();
    }

    @DeleteMapping("/{id}")
    public Account deleteAccount(@PathVariable("id") int id) {
        return new Account();
    }
}
