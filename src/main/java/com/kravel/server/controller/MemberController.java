package com.kravel.server.controller;

import com.kravel.server.auth.dto.SignUpDTO;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.LogHandler;
import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.GuestTokenDTO;
import com.kravel.server.dto.MemberDTO;
import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.update.InquireUploadDTO;
import com.kravel.server.dto.update.MemberUpdateDTO;
import com.kravel.server.enums.InquireCategory;
import com.kravel.server.enums.Speech;
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
import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<Message> signUpMember(@RequestBody SignUpDTO signUpDTO,
                                                HttpServletRequest request) throws Exception {

        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        MemberDTO memberDTO = memberService.saveMember(signUpDTO);
        return ResponseEntity.status(HttpStatus.OK).header(
                "Authorization", "Bearer " + memberDTO.getToken())
                .body(new Message(memberDTO));
    }

    @GetMapping("/auth/refresh-token")
    public ResponseEntity<Message> refreshToken(HttpServletRequest request, HttpServletResponse res) throws Exception {

        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        String tokenPayload = request.getHeader("Authorization");

        String token = memberService.refreshToken(tokenPayload);
        return ResponseEntity.status(HttpStatus.OK).header(
                "Authorization", "Bearer " + token)
                .body(new Message("Refresh token generated succeed"));
    }

    @PostMapping("/auth/guest")
    public ResponseEntity<Message> guestToken(HttpServletRequest request,
                                              @RequestBody GuestTokenDTO guestTokenDTO) throws Exception {

        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        String guestToken = memberService.getGuestToken(guestTokenDTO);

        return ResponseEntity.status(HttpStatus.OK).header(
                "Authorization", "Bearer " + guestToken)
                .body(new Message("Refresh token generated succeed"));
    }

    @GetMapping("/api/members/me")
    public ResponseEntity<Message> findById(Authentication authentication, HttpServletRequest request) throws Exception {

        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        long memberId = claimExtractor.getMemberId(authentication);

        MemberDTO memberDTO = memberService.findById(memberId);
        return ResponseEntity.ok(new Message(memberDTO));
    }

    @GetMapping("/admin/members")
    public ResponseEntity<Message> findAllMember(@PageableDefault Pageable pageable,
                                                 HttpServletRequest request) throws Exception {

        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

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
            case "password":
                memberDTO = memberService.modifyMemberLoginPw(memberId, memberUpdateDTO);
                return ResponseEntity.ok(new Message(memberDTO));

            case "nickNameAndGender":
                memberDTO = memberService.modifyMemberNickName(memberId, memberUpdateDTO);
                return ResponseEntity.status(HttpStatus.OK).header(
                        "Authorization", "Bearer " + memberDTO.getToken())
                        .body(new Message(memberDTO));

            case "speech":
                memberDTO = memberService.modifyMemberSpeech(memberId, memberUpdateDTO);
                return ResponseEntity.status(HttpStatus.OK).header(
                        "Authorization", "Bearer " + memberDTO.getToken())
                        .body(new Message(memberDTO));

            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Message(
                            new InvalidRequestException("유효하지 않는 값입니다."),
                            req.getRequestURL().toString()
                        )
                );
        }

    }

    @DeleteMapping("/api/member")
    public ResponseEntity<Message> removeMember(@RequestBody MemberDTO memberDTO,
                                                Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(memberService.removeMember(memberId, memberDTO)));
    }

    @GetMapping("/api/member/scraps")
    public ResponseEntity<Message> findAllScrapById(@PageableDefault Pageable pageable,
                                                 Authentication authentication) throws Exception {

        long memberId = claimExtractor.getMemberId(authentication);
        Speech speech = claimExtractor.getSpeech(authentication);
        Page<PlaceDTO> placeDTOs = memberService.findAllScrapById(memberId, pageable, speech);

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
