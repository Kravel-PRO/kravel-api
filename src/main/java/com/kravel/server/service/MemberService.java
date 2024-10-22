package com.kravel.server.service;

import com.kravel.server.auth.dto.SignUpDTO;
import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.security.util.exception.InvalidJwtException;
import com.kravel.server.auth.security.util.jwt.HeaderTokenExtractor;
import com.kravel.server.auth.security.util.jwt.JwtFactory;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.InternalServerException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.GuestTokenDTO;
import com.kravel.server.dto.MemberDTO;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.update.InquireUploadDTO;
import com.kravel.server.dto.update.MemberUpdateDTO;
import com.kravel.server.enums.RoleType;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.member.*;
import com.kravel.server.model.place.Place;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    private final HeaderTokenExtractor headerTokenExtractor;
    private final RememberMeQueryRepository rememberMeQueryRepository;
    private final RememberMeRepository rememberMeRepository;
    private final S3Uploader s3Uploader;

    @Value("${keys.jwt.secret-key}")
    private String secretKey;

    @Transactional
    public MemberDTO saveMember(SignUpDTO signUpDTO) throws Exception {
        if (signUpDTO.getLoginEmail().isEmpty()
                || signUpDTO.getLoginPw().isEmpty()
                || signUpDTO.getGender().isEmpty()
                || signUpDTO.getNickName().isEmpty()
                || signUpDTO.getSpeech().name().isEmpty()) {
            throw new InvalidRequestException("🔥 error: have not enough information");
        }
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
        memberRepository.save(member);

        String token = jwtFactory.generateToken(MemberContext.fromMemberModel(member));
        RememberMe rememberMe = RememberMe.builder()
                .member(member)
                .token(token)
                .build();
        rememberMeRepository.save(rememberMe);

        MemberDTO memberDTO = MemberDTO.fromEntity(member);
        memberDTO.setToken(token);
        return memberDTO;
    }

    public Page<MemberDTO>findAllMember(Pageable pageable) throws Exception {
        Page<Member> memberPage = memberRepository.findAll(pageable);
        Page<MemberDTO> memberDTOs = (Page<MemberDTO>) memberPage.stream().map(MemberDTO::fromEntity).collect(Collectors.toList());
        return memberDTOs;
    }

    @Transactional
    public MemberDTO modifyMemberLoginPw(long memberId, MemberUpdateDTO memberUpdateDTO) throws Exception {

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new NotFoundException("🔥 error: is not exist member")
        );
        if (!isCorrectPassword(memberUpdateDTO.getLoginPw(), savedMember)) {
            throw new InvalidRequestException("🔥 error: is not correct password");
        }
        savedMember.changeLoginPw(passwordEncoder.encode(memberUpdateDTO.getModifyLoginPw()));

        return MemberDTO.fromEntity(savedMember);
    }

    private boolean isCorrectPassword(String password, Member member) {
        return passwordEncoder.matches(password, member.getLoginPw());
    }

    @Transactional
    public MemberDTO modifyMemberNickName(long memberId, MemberUpdateDTO memberUpdateDTO) throws Exception {

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new NotFoundException("🔥 error: is not exist member")
        );

        savedMember.changeNickName(memberUpdateDTO.getNickName());
        savedMember.changeGender(memberUpdateDTO.getGender());
        return getMemberDTO(savedMember);
    }

    @Transactional
    public MemberDTO modifyMemberSpeech(long memberId, MemberUpdateDTO memberUpdateDTO) throws Exception {

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
            new NotFoundException("🔥 error: is not exist member")
        );

        savedMember.changeSpeech(memberUpdateDTO.getSpeech());
        return getMemberDTO(savedMember);
    }

    private MemberDTO getMemberDTO(Member savedMember) {

        String token = jwtFactory.generateToken(MemberContext.fromMemberModel(savedMember));
        savedMember.getRememberMe().updateToken(token);

        MemberDTO memberDTO = MemberDTO.fromEntity(savedMember);
        memberDTO.setToken(token);

        return memberDTO;
    }

    public int removeMember(long memberId, MemberDTO memberDTO) throws Exception {

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() -> new InvalidRequestException("🔥 error: is not correct password"));;
        memberRepository.delete(savedMember);

        return 1;
    }

    public Page<PlaceDTO> findAllScrapById(long memberId, Pageable pageable, Speech speech) {
        Page<Place> places = memberQueryRepository.findAllScrapById(memberId, pageable);
        return places.map(new Function<Place, PlaceDTO>() {
            @Override
            public PlaceDTO apply(Place place) {
                place.findInfoSpeech(speech);
                return PlaceDTO.fromEntity(place);
            }
        });
    }

    public long saveInquire(InquireUploadDTO inquireUploadDTO, long memberId) throws Exception {
        if (inquireUploadDTO.getFiles().isEmpty()) {
            throw new InvalidRequestException("🔥 error: is not exist image file");
        }
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new InvalidRequestException("is not exist member")
        );
        Inquire inquire = new Inquire(inquireUploadDTO, member);
        inquire.saveImages(s3Uploader, inquireUploadDTO.getFiles());

        Inquire savedInquire = inquireRepository.save(inquire);
        if (savedInquire.getId() == 0) {
            throw new InterruptedException("🔥 error: save inquire exception");
        }
        return savedInquire.getId();
    }

    public MemberDTO findById(long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new InvalidRequestException("🔥error: is not exist member"));
        return MemberDTO.fromEntity(member);
    }


    public String refreshToken(String tokenPayload) {
        String token = headerTokenExtractor.extract(tokenPayload);
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).setAllowedClockSkewSeconds(6000 * 14).parseClaimsJws(token);

        long memberId = claims.getBody().get("member_id", Long.class);
        RememberMe rememberMe = rememberMeQueryRepository.findByMember(memberId)
                .orElseThrow(() ->
                        new InvalidJwtException("유효하지 않는 refresh token입니다.")
                );

        String jwt = jwtFactory.generateToken(MemberContext.fromMemberModel(rememberMe.getMember()));
        rememberMe.updateToken(jwt);
        return jwt;
    }

    public String getGuestToken(GuestTokenDTO guestTokenDTO) throws Exception {
        return jwtFactory.generateGuestToken(guestTokenDTO.getSpeech().name());
    }
}