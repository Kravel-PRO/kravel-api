package com.kravel.server.config;

import com.kravel.server.auth.security.token.PostAuthorizationToken;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {

    private final ClaimExtractor claimExtractor;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAware<Long>() {
            @Override
            public Optional<Long> getCurrentAuditor() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                long memberId = claimExtractor.getMemberId(authentication);
//                Member savedMember = memberRepository.findById(memberId).orElse(new Member());
                System.out.println("===========================================================");
                Member member = Member.builder().id(memberId).build();

                return Optional.of(memberId);
            }
        };
    }
}
