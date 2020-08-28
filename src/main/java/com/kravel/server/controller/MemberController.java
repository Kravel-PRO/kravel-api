package com.kravel.server.controller;

import com.kravel.server.auth.dto.SignUpDTO;
import com.kravel.server.dto.MemberDTO;
import com.kravel.server.service.MemberService;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/auth/state")
    @ResponseStatus(HttpStatus.OK)
    public String getState() throws Exception {
        return "SERVER IS RUNNING";
    }

    @PostMapping("/auth/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage signUpMember(@RequestBody SignUpDTO signUpDTO) throws Exception {

        return new ResponseMessage(HttpStatus.OK, memberService.saveMember(signUpDTO));
    }

    @PutMapping("/api/member/{loginEmail}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage modifyMember(HttpServletRequest req,
                                        @PathVariable("loginEmail") String loginEmail,
                                        @RequestParam("type") String type,
                                        @RequestBody MemberDTO memberDTO) throws Exception {
        long memberId;
        switch (type) {
            case "password": memberId = memberService.modifyMemberLoginPw(loginEmail, memberDTO);
                break;

            case "nickname": memberId = memberService.modifyMemberNickName(loginEmail, memberDTO);
                break;

            default:
                return new ResponseMessage(
                        new InvalidRequestException("유효하지 않는 값입니다."),
                        req.getRequestURL().toString());
        }

        return new ResponseMessage(HttpStatus.CREATED, memberId);
    }

    @DeleteMapping("/api/member/{loginEmail}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseMessage removeMember(@PathVariable("loginEmail") String loginEmail, @RequestBody MemberDTO memberDTO) throws Exception {
        return new ResponseMessage(HttpStatus.ACCEPTED, memberService.removeMember(loginEmail, memberDTO));
    }
}
