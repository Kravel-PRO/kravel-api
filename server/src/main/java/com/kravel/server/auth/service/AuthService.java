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
        member.setGender(member.getGender().toUpperCase());

        return memberMapper.saveMember(member) != 0;
    }

    public boolean updateMemberLoginPw(String loginEmail, Member member) throws Exception {

        Member savedMember = memberMapper.findByMemberId(loginEmail);
        if (!passwordEncoder.matches(member.getComparedCurPw(), savedMember.getLoginPw())) {
            throw new InvalidRequestException("Password is not correct!");
        }

        member.setLoginPw(encodePassword(member));
        return memberMapper.updateMemberLoginPw(loginEmail, member) != 0;
    }

    public boolean updateMemberNickName(String loginEmail, Member member) throws Exception {

        return memberMapper.updateMemberNickName(loginEmail, member) != 0;
    }

    public boolean deleteMember(String loginEmail, Member member) throws Exception {

        Member savedMember = memberMapper.findByMemberId(loginEmail);
        if (!passwordEncoder.matches(member.getLoginPw(), savedMember.getLoginPw())) {
            throw new InvalidRequestException("Password is not correct!");
        }

        return memberMapper.deleteMember(loginEmail) != 0;
    }

}
