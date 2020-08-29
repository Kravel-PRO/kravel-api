package com.kravel.server.auth.service;

import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.member.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberContextService implements UserDetailsService {

    private final MemberQueryRepository memberQueryRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Member member = memberQueryRepository.findMemberByLoginEmail(username).orElseThrow(() -> new UsernameNotFoundException("Member is empty!"));
            return getMemberContext(member);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private MemberContext getMemberContext(Member member) {
        return MemberContext.fromMemberModel(member);
    }
}
