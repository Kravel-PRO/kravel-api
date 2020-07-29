package com.kravel.server.auth.service;

import com.kravel.server.auth.model.Member;
import com.kravel.server.auth.mapper.AuthMapper;
import com.kravel.server.auth.model.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberContextService implements UserDetailsService {

    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Member member = authMapper.findByLoginEmail(username);
            if (member.getLoginEmail().isEmpty()) {
                throw new UsernameNotFoundException("Member is empty!");
            }

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
