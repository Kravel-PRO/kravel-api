package com.kravel.server.service;

import com.kravel.server.dto.MemberDTO;
import com.kravel.server.mapper.MemberMapper;
import com.kravel.server.auth.mapper.AuthMapper;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberQueryRepository;
import com.kravel.server.model.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    public long modifyMemberLoginPw(String loginEmail, MemberDTO memberDTO) throws Exception {

        Member savedMember = memberQueryRepository.findMemberByLoginEmail(loginEmail);
        if (!passwordEncoder.matches(memberDTO.getLoginPw(), savedMember.getLoginPw())) {
            throw new InvalidRequestException("🔥 error: is not correct password");
        }

        savedMember.changeLoginPw(passwordEncoder.encode(memberDTO.getLoginPw()));
        return savedMember.getId();
    }

    public long modifyMemberNickName(String loginEmail, MemberDTO memberDTO) throws Exception {

        Member savedMember = memberQueryRepository.findMemberByLoginEmail(loginEmail);
        savedMember.changeNickName(memberDTO.getNickName());
        return savedMember.getId();
    }

    public int removeMember(String loginEmail, MemberDTO memberDTO) throws Exception {

        Member savedMember = memberQueryRepository.findMemberByLoginEmail(loginEmail);
        if (!passwordEncoder.matches(memberDTO.getLoginPw(), savedMember.getLoginPw())) {
            throw new InvalidRequestException("🔥 error: is not correct password");
        }

        memberRepository.delete(savedMember);

        return 1;
    }
}
