package com.kravel.server.controller;

import com.kravel.server.auth.dto.SignUpDTO;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.MemberDTO;
import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.update.InquireUploadDTO;
import com.kravel.server.dto.update.MemberUpdateDTO;
import com.kravel.server.enums.InquireCategory;
import com.kravel.server.service.MemberService;
import com.kravel.server.common.util.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ClaimExtractor claimExtractor;

    @GetMapping("/auth/state")
    public String getState() throws Exception {
        return "SERVER IS RUNNING";
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<Message> signUpMember(@RequestBody SignUpDTO signUpDTO) throws Exception {
        long memberId = memberService.saveMember(signUpDTO);

        return ResponseEntity.ok(new Message(memberId));
    }

    @GetMapping("/api/members/me")
    public ResponseEntity<Message> findById(Authentication authentication) throws Exception {
        long memberId = claimExtractor.getMemberId(authentication);

        MemberDTO memberDTO = memberService.findById(memberId);
        return ResponseEntity.ok(new Message(memberDTO));
    }

    @GetMapping("/admin/members")
    public ResponseEntity<Message> findAllMember(@PageableDefault Pageable pageable) throws Exception {
        Page<MemberDTO> memberPage = memberService.findAllMember(pageable);
        return ResponseEntity.ok(new Message(memberPage));
    }

    @PutMapping("/api/member")
    public ResponseEntity<Message> modifyMember(HttpServletRequest req,
                                        @RequestParam("type") String type,
                                        @RequestBody MemberUpdateDTO memberUpdateDTO,
                                                Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        MemberDTO memberDTO;
        switch (type) {
            case "password": memberDTO = memberService.modifyMemberLoginPw(memberId, memberUpdateDTO);
                break;

            case "nickNameAndGender": memberDTO = memberService.modifyMemberNickName(memberId, memberUpdateDTO);
                break;

            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Message(
                            new InvalidRequestException("유효하지 않는 값입니다."),
                            req.getRequestURL().toString()
                        )
                );
        }

        return ResponseEntity.ok(new Message(memberDTO));
    }

    @DeleteMapping("/api/member")
    public ResponseEntity<Message> removeMember(@PathVariable("loginEmail") String loginEmail,
                                                @RequestBody MemberDTO memberDTO,
                                                Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(memberService.removeMember(memberId, memberDTO)));
    }

    @GetMapping("/api/member/scraps")
    public ResponseEntity<Message> findAllScrapById(@PageableDefault Pageable pageable,
                                                 Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        Page<PlaceDTO> placeDTOs = memberService.findAllScrapById(memberId, pageable);

        return ResponseEntity.ok(new Message(placeDTOs));
    }

    @PostMapping("/api/member/inquires")
    public ResponseEntity<Message> saveInquires(@ModelAttribute InquireUploadDTO inquireUploadDTO,
                                                Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        long inquireId = memberService.saveInquire(inquireUploadDTO, memberId);

        return ResponseEntity.ok(new Message(inquireId));
    }

}
