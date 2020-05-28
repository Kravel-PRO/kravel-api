package com.kravel.server.api.account.controller;

import com.kravel.server.auth.model.Account;
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

    @PutMapping("/{id}")
    public Account modifyAccount(@PathVariable("id") int id) {
        return new Account();
    }

    @DeleteMapping("/{id}")
    public Account deleteAccount(@PathVariable("id") int id) {
        return new Account();
    }
}
