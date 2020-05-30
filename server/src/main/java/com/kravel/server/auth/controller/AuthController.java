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
    public ResponseMessage signUpAccount(HttpServletRequest req, @RequestBody Account account) throws Exception {
        return new ResponseMessage(HttpStatus.OK, authService.signUpAccount(account));
    }

    @PutMapping("/{accountId}")
    public ResponseMessage updateAccount(@PathVariable("accountId") int accountId, @RequestBody Account account) throws Exception {
        return new ResponseMessage(HttpStatus.CREATED, authService.updateAccount(accountId, account));
    }

    @DeleteMapping("/{accountId}")
    public ResponseMessage deleteAccount(@PathVariable("accountId") int accountId, @RequestBody Account account) throws Exception {
        return new ResponseMessage(HttpStatus.ACCEPTED, authService.deleteAccount(accountId, account));
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage exceptionHandler(HttpServletRequest req, Exception e) {
        return new ResponseMessage(new InvalidRequestException(e.getMessage(), e), req.getRequestURL().toString());
    }
}
