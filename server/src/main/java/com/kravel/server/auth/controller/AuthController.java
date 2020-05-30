package com.kravel.server.auth.controller;

import com.kravel.server.auth.model.Account;
import com.kravel.server.auth.service.AuthService;
import com.kravel.server.common.util.exception.InvalidRequestException;
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
//    @ExceptionHandler(InvalidRequestException.class)
    public ResponseMessage selectAccount(HttpServletRequest req,
                                         Exception exception,

                                         @RequestBody Account account) throws Exception {

        if (authService.signUpAccount(account) > 0) {
            return new ResponseMessage(HttpStatus.OK);
        }

        return new ResponseMessage(
                new InvalidRequestException("hi"),
                req.getRequestURL().toString()
        );
    }
}
