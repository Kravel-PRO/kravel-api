package com.kravel.server.auth.controller;

import com.kravel.server.auth.mapper.AccountMapper;
import com.kravel.server.auth.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AccountMapper accountMapper;

    @PostMapping("/{id}")
    public void selectAccount(@PathVariable("id") String loginEmail) {
        Account account = accountMapper.findByLoginEmail(loginEmail);
        System.out.println(account);
        System.out.println("HelloWorld");
    }
}
