package com.kravel.server.common;

import com.kravel.server.auth.security.token.PostAuthorizationToken;
import com.kravel.server.auth.service.MemberContextService;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberRepository;
import com.kravel.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<Member> {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        PostAuthorizationToken postAuthorizationToken = (PostAuthorizationToken) authentication;
        Member member = memberRepository.findById(postAuthorizationToken.getMemberContext().getMember().getId()).get();
        return Optional.ofNullable(member);
    }
}
