//package com.kravel.server.auth.service;
//
//import com.kravel.server.auth.dto.SignUpDTO;
//import com.kravel.server.auth.mapper.AuthMapper;
//import com.kravel.server.common.util.exception.InvalidRequestException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final AuthMapper authMapper;
//    private final PasswordEncoder passwordEncoder;
//
//    public boolean signUpMember(SignUpDTO signUpDTO) throws Exception {
//
//        if (authMapper.checkDuplicateLoginEmail(signUpDTO.getLoginEmail()) > 0) {
//            throw new InvalidRequestException("Login Email is already exist");
//        }
//
//        Member member = new Member();
//        member.setLoginEmail(signUpDTO.getLoginEmail());
//        member.setLoginPw(signUpDTO.getLoginPw());
//        member.setNickName(signUpDTO.getNickName());
//        member.setGender(signUpDTO.getGender());
//
//        member.setLoginPw(passwordEncoder.encode(member.getLoginPw()));
//        member.setGender(member.getGender().toUpperCase());
//
//        return authMapper.saveMember(member) != 0;
//    }
//
//
//}
