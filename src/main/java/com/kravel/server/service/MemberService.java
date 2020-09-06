package com.kravel.server.service;

import com.kravel.server.auth.dto.SignUpDTO;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.MemberDTO;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.place.PlaceDetailDTO;
import com.kravel.server.dto.update.MemberUpdateDTO;
import com.kravel.server.enums.RoleType;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberQueryRepository;
import com.kravel.server.model.member.MemberRepository;
import com.kravel.server.model.place.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    public long saveMember(SignUpDTO signUpDTO) throws Exception {
        Optional<Member> optionalMember = memberQueryRepository.findMemberByLoginEmail(signUpDTO.getLoginEmail());
        if (optionalMember.isPresent()) {
            throw new InvalidRequestException("🔥 error: login email is exist!");
        }
        System.out.println("===================");
        Member member = Member.builder()
                .loginEmail(signUpDTO.getLoginEmail())
                .loginPw(passwordEncoder.encode(signUpDTO.getLoginPw()))
                .nickName(signUpDTO.getNickName())
                .roleType(RoleType.USER)
                .gender(signUpDTO.getGender().toUpperCase())
                .speech(signUpDTO.getSpeech().toUpperCase())
                .build();
        System.out.println("===================");

        System.out.println(member.getNickName());
        System.out.println(member.getNickName());
        System.out.println(member.getGender());
        System.out.println(member.getLoginEmail());
        return memberRepository.save(member).getId();
    }

    public Page<MemberDTO>findAllMember(Pageable pageable) throws Exception {
        Page<Member> memberPage = memberRepository.findAll(pageable);
        Page<MemberDTO> memberDTOs = (Page<MemberDTO>) memberPage.stream().map(MemberDTO::fromEntity).collect(Collectors.toList());
        return memberDTOs;
    }

    public long modifyMemberLoginPw(String loginEmail, MemberUpdateDTO memberUpdateDTO) throws Exception {

        Member savedMember = memberQueryRepository.findMemberByLoginEmail(loginEmail).orElseThrow(() -> new InvalidRequestException("🔥 error: is not correct password"));

        savedMember.changeLoginPw(passwordEncoder.encode(memberUpdateDTO.getLoginPw()));
        return savedMember.getId();
    }

    public long modifyMemberNickName(String loginEmail, MemberUpdateDTO memberUpdateDTO) throws Exception {

        Member savedMember = memberQueryRepository.findMemberByLoginEmail(loginEmail).orElseThrow(() -> new NotFoundException("🔥 error: is not exist member"));
        savedMember.changeNickName(memberUpdateDTO.getNickName());
        savedMember.changeGender(memberUpdateDTO.getGender());
        return savedMember.getId();
    }

    public int removeMember(String loginEmail, MemberDTO memberDTO) throws Exception {

        Member savedMember = memberQueryRepository.findMemberByLoginEmail(loginEmail).orElseThrow(() -> new InvalidRequestException("🔥 error: is not correct password"));;
        memberRepository.delete(savedMember);

        return 1;
    }

    public Page<PlaceDTO> findAllScrapById(long memberId, Pageable pageable) {
        Page<Place> places = memberQueryRepository.findAllScrapById(memberId, pageable);
        return places.map(new Function<Place, PlaceDTO>() {
            @Override
            public PlaceDTO apply(Place place) {
                return PlaceDTO.fromEntity(place);
            }
        });
    }
}
