package com.kravel.server.auth.controller;

import com.kravel.server.auth.model.Member;
import com.kravel.server.auth.service.AuthService;
import com.kravel.server.common.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage signUpMember(HttpServletRequest req, @RequestBody Member member) throws Exception {
        return new ResponseMessage(HttpStatus.OK, authService.signUpMember(member));
    }
}
