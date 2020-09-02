package com.kravel.server.controller;

import com.kravel.server.auth.dto.SignUpDTO;
import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.MemberDTO;
import com.kravel.server.dto.update.MemberUpdateDTO;
import com.kravel.server.service.MemberService;
import com.kravel.server.common.util.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/auth/state")
    public String getState() throws Exception {
        return "SERVER IS RUNNING";
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<Message> signUpMember(@RequestBody SignUpDTO signUpDTO) throws Exception {
        long memberId = memberService.saveMember(signUpDTO);

        return ResponseEntity.ok(new Message(memberId));
    }

    @GetMapping("/admin/members")
    public ResponseEntity<Message> findAllMember(@PageableDefault Pageable pageable) throws Exception {
        Page<MemberDTO> memberPage = memberService.findAllMember(pageable);
        return ResponseEntity.ok(new Message(memberPage));
    }

    @PutMapping("/api/member/{loginEmail}")
    public ResponseEntity<Message> modifyMember(HttpServletRequest req,
                                        @PathVariable("loginEmail") String loginEmail,
                                        @RequestParam("type") String type,
                                        @RequestBody MemberUpdateDTO memberUpdateDTO) throws Exception {
        long memberId;
        switch (type) {
            case "password": memberId = memberService.modifyMemberLoginPw(loginEmail, memberUpdateDTO);
                break;

            case "nickNameAndGender": memberId = memberService.modifyMemberNickName(loginEmail, memberUpdateDTO);
                break;

            default:
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(new Message(
                            new InvalidRequestException("유효하지 않는 값입니다."),
                            req.getRequestURL().toString()
                        )
                );
        }

        return ResponseEntity.ok(new Message(memberId));
    }

    @DeleteMapping("/api/member/{loginEmail}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Message> removeMember(@PathVariable("loginEmail") String loginEmail, @RequestBody MemberDTO memberDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(memberService.removeMember(loginEmail, memberDTO)));
    }
}
