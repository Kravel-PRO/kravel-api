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
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage signUpAccount(HttpServletRequest req, @RequestBody Account account) throws Exception {
        return new ResponseMessage(HttpStatus.OK, authService.signUpAccount(account));
    }

    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage updateAccount(HttpServletRequest req,
                                         @PathVariable("accountId") int accountId,
                                         @RequestParam("type") String type,
                                         @RequestBody Account account) throws Exception {
        boolean result ;
        switch (type) {
            case "password": result = authService.updateAccountLoginPw(accountId, account);
                break;

            case "nickname": result = authService.updateAccountNickName(accountId, account);
                break;

            default:
                return new ResponseMessage(
                        new InvalidRequestException("유효하지 않는 값입니다."),
                        req.getRequestURL().toString());
        }

        return new ResponseMessage(HttpStatus.CREATED, result);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseMessage deleteAccount(@PathVariable("accountId") int accountId, @RequestBody Account account) throws Exception {
        return new ResponseMessage(HttpStatus.ACCEPTED, authService.deleteAccount(accountId, account));
    }

}
