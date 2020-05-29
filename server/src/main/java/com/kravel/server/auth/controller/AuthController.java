package com.kravel.server.auth.controller;

import com.kravel.server.auth.model.Account;
import com.kravel.server.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public int selectAccount(@RequestBody Account account) throws Exception {
        return authService.signUpAccount(account);
    }
}
