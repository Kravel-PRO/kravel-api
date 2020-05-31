package com.kravel.server.auth.controller;

import com.kravel.server.auth.model.Member;
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
    public ResponseMessage signUpMember(HttpServletRequest req, @RequestBody Member member) throws Exception {
        return new ResponseMessage(HttpStatus.OK, authService.signUpMember(member));
    }

    @PutMapping("/{memberId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage updateMember(HttpServletRequest req,
                                         @PathVariable("loginEmail") String loginEmail,
                                         @RequestParam("type") String type,
                                         @RequestBody Member member) throws Exception {
        boolean result ;
        switch (type) {
            case "password": result = authService.updateMemberLoginPw(loginEmail, member);
                break;

            case "nickname": result = authService.updateMemberNickName(loginEmail, member);
                break;

            default:
                return new ResponseMessage(
                        new InvalidRequestException("유효하지 않는 값입니다."),
                        req.getRequestURL().toString());
        }

        return new ResponseMessage(HttpStatus.CREATED, result);
    }

    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseMessage deleteMember(@PathVariable("loginEmail") String loginEmail, @RequestBody Member member) throws Exception {
        return new ResponseMessage(HttpStatus.ACCEPTED, authService.deleteMember(loginEmail, member));
    }

}
