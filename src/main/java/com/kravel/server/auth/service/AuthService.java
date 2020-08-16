package com.kravel.server.auth.service;

import com.kravel.server.auth.dto.SignUpDTO;
import com.kravel.server.auth.mapper.AuthMapper;
import com.kravel.server.auth.model.Member;
import com.kravel.server.common.util.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    public boolean signUpMember(SignUpDTO signUpDTO) throws Exception {

        if (authMapper.checkDuplicateLoginEmail(signUpDTO.getLoginEmail()) > 0) {
            throw new InvalidRequestException("Login Email is already exist");
        }

        Member member = Member.builder()
                .loginEmail(signUpDTO.getLoginEmail())
                .loginPw(signUpDTO.getLoginPw())
                .nickName(signUpDTO.getNickName())
                .gender(signUpDTO.getGender())
                .build();

        member.changeLoginPw(passwordEncoder.encode(member.getLoginPw()));
        member.changeGender(member.getGender().toUpperCase());

        return authMapper.saveMember(member) != 0;
    }


}
