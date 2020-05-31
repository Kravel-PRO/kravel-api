package com.kravel.server.auth.controller;

import com.kravel.server.auth.model.Member;
import com.kravel.server.auth.service.AuthService;
import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage signUpMember(HttpServletRequest req, @RequestBody Member member) throws Exception {
        return new ResponseMessage(HttpStatus.OK, authService.signUpMember(member));
    }
}
