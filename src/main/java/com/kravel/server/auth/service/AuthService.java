package com.kravel.server.auth.service;

import com.kravel.server.auth.mapper.AuthMapper;
import com.kravel.server.auth.model.Member;
import com.kravel.server.common.util.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    public boolean signUpMember(Member member) throws Exception {

        if (authMapper.checkDuplicateLoginEmail(member.getLoginEmail()) > 0) {
            throw new InvalidRequestException("Login Email is already exist");
        }

        member.setLoginPw(passwordEncoder.encode(member.getLoginPw()));
        member.setGender(member.getGender().toUpperCase());

        return authMapper.saveMember(member) != 0;
    }


}
