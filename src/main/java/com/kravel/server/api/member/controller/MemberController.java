package com.kravel.server.api.member.controller;

import com.kravel.server.api.member.service.MemberService;
import com.kravel.server.auth.model.Member;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private MemberService memberService;

    MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PutMapping("/{loginEmail}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage modifyMember(HttpServletRequest req,
                                        @PathVariable("loginEmail") String loginEmail,
                                        @RequestParam("type") String type,
                                        @RequestBody Member member) throws Exception {
        boolean result ;
        switch (type) {
            case "password": result = memberService.modifyMemberLoginPw(loginEmail, member);
                break;

            case "nickname": result = memberService.modifyMemberNickName(loginEmail, member);
                break;

            default:
                return new ResponseMessage(
                        new InvalidRequestException("유효하지 않는 값입니다."),
                        req.getRequestURL().toString());
        }

        return new ResponseMessage(HttpStatus.CREATED, result);
    }

    @DeleteMapping("/{loginEmail}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseMessage removeMember(@PathVariable("loginEmail") String loginEmail, @RequestBody Member member) throws Exception {
        return new ResponseMessage(HttpStatus.ACCEPTED, memberService.removeMember(loginEmail, member));
    }
}