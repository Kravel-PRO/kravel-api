package com.kravel.server.api.member.service;

import com.kravel.server.api.member.mapper.MemberMapper;
import com.kravel.server.auth.mapper.AuthMapper;
import com.kravel.server.auth.model.Member;
import com.kravel.server.common.util.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private AuthMapper authMapper;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean modifyMemberLoginPw(String loginEmail, Member member) throws Exception {

        Member savedMember = authMapper.findByLoginEmail(loginEmail);
        if (!passwordEncoder.matches(member.getComparedCurPw(), savedMember.getLoginPw())) {
            throw new InvalidRequestException("Password is not correct!");
        }

        member.setLoginPw(passwordEncoder.encode(member.getLoginPw()));
        return memberMapper.modifyMemberLoginPw(loginEmail, member) != 0;
    }

    public boolean modifyMemberNickName(String loginEmail, Member member) throws Exception {

        return memberMapper.modifyMemberNickName(loginEmail, member) != 0;
    }

    public boolean removeMember(String loginEmail, Member member) throws Exception {

        Member savedMember = authMapper.findByMemberId(loginEmail);
        if (!passwordEncoder.matches(member.getLoginPw(), savedMember.getLoginPw())) {
            throw new InvalidRequestException("Password is not correct!");
        }

        return memberMapper.removeMember(loginEmail) != 0;
    }
}
