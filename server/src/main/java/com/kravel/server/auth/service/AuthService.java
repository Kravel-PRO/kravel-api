package com.kravel.server.auth.service;

import com.kravel.server.auth.mapper.MemberMapper;
import com.kravel.server.auth.model.Member;
import com.kravel.server.common.util.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String encodePassword(Member member) {
        return passwordEncoder.encode(member.getLoginPw());
    }

    public boolean signUpMember(Member member) throws Exception {

        if (memberMapper.checkDuplicateLoginEmail(member.getLoginEmail()) > 0) {
            throw new InvalidRequestException("Login Email is already exist");
        }

        member.setLoginPw(encodePassword(member));
        return memberMapper.saveMember(member) != 0;
    }

    public boolean updateMemberLoginPw(int memberId, Member member) throws Exception {

        Member savedMember = memberMapper.findByMemberId(memberId);
        if (!passwordEncoder.matches(member.getComparedCurPw(), savedMember.getLoginPw())) {
            throw new InvalidRequestException("Password is not correct!");
        }

        member.setLoginPw(encodePassword(member));
        return memberMapper.updateMemberLoginPw(memberId, member) != 0;
    }

    public boolean updateMemberNickName(int memberId, Member member) throws Exception {

        return memberMapper.updateMemberNickName(memberId, member) != 0;
    }

    public boolean deleteMember(int memberId, Member member) throws Exception {

        Member savedMember = memberMapper.findByMemberId(memberId);
        if (!passwordEncoder.matches(member.getLoginPw(), savedMember.getLoginPw())) {
            throw new InvalidRequestException("Password is not correct!");
        }

        return memberMapper.deleteMember(memberId) != 0;
    }

}
