package com.kravel.server.service;

import com.kravel.server.auth.dto.SignUpDTO;
import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.security.util.jwt.JwtFactory;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.MemberDTO;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.place.PlaceDetailDTO;
import com.kravel.server.dto.update.InquireUploadDTO;
import com.kravel.server.dto.update.MemberUpdateDTO;
import com.kravel.server.enums.RoleType;
import com.kravel.server.model.member.*;
import com.kravel.server.model.place.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final InquireRepository inquireRepository;
    private final JwtFactory jwtFactory;

    private final S3Uploader s3Uploader;

    public long saveMember(SignUpDTO signUpDTO) throws Exception {
        Optional<Member> optionalMember = memberQueryRepository.findMemberByLoginEmail(signUpDTO.getLoginEmail());
        if (optionalMember.isPresent()) {
            throw new InvalidRequestException("🔥 error: login email is exist!");
        }
        Member member = Member.builder()
                .loginEmail(signUpDTO.getLoginEmail())
                .loginPw(passwordEncoder.encode(signUpDTO.getLoginPw()))
                .nickName(signUpDTO.getNickName())
                .roleType(RoleType.USER)
                .gender(signUpDTO.getGender().toUpperCase())
                .speech(signUpDTO.getSpeech())
                .build();

        return memberRepository.save(member).getId();
    }

    public Page<MemberDTO>findAllMember(Pageable pageable) throws Exception {
        Page<Member> memberPage = memberRepository.findAll(pageable);
        Page<MemberDTO> memberDTOs = (Page<MemberDTO>) memberPage.stream().map(MemberDTO::fromEntity).collect(Collectors.toList());
        return memberDTOs;
    }

    public MemberDTO modifyMemberLoginPw(long memberId, MemberUpdateDTO memberUpdateDTO) throws Exception {

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() -> new InvalidRequestException("🔥 error: is not exist member"));
        if (!isCorrectPassword(memberUpdateDTO.getLoginPw(), savedMember)) {
            throw new InvalidRequestException("🔥 error: is not correct password");
        }
        savedMember.changeLoginPw(passwordEncoder.encode(memberUpdateDTO.getModifyLoginPw()));
        memberRepository.save(savedMember);

        return MemberDTO.fromEntity(savedMember);
    }

    private boolean isCorrectPassword(String password, Member member) {
        return passwordEncoder.matches(password, member.getLoginPw());
    }

    public MemberDTO modifyMemberNickName(long memberId, MemberUpdateDTO memberUpdateDTO) throws Exception {

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist member"));

        savedMember.changeNickName(memberUpdateDTO.getNickName());
        savedMember.changeGender(memberUpdateDTO.getGender());
        memberRepository.save(savedMember);

        return MemberDTO.fromEntity(savedMember);
    }

    public MemberDTO modifyMemberSpeech(long memberId, MemberUpdateDTO memberUpdateDTO) {

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist member"));

        savedMember.changeSpeech(memberUpdateDTO.getSpeech());
        memberRepository.save(savedMember);

        MemberDTO memberDTO = MemberDTO.fromEntity(savedMember);
        memberDTO.setToken(jwtFactory.generateToken(MemberContext.fromMemberModel(savedMember)));
        return memberDTO;
    }

    public int removeMember(long memberId, MemberDTO memberDTO) throws Exception {

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() -> new InvalidRequestException("🔥 error: is not correct password"));;
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

    public long saveInquire(InquireUploadDTO inquireUploadDTO, long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new InvalidRequestException("is not exist member"));
        Inquire inquire = new Inquire(inquireUploadDTO, member);
        inquire.saveImages(s3Uploader, inquireUploadDTO.getFiles());

        Inquire savedInquire = inquireRepository.save(inquire);
        return savedInquire.getId();
    }

    public MemberDTO findById(long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new InvalidRequestException("🔥error: is not exist member"));
        return MemberDTO.fromEntity(member);
    }


}
