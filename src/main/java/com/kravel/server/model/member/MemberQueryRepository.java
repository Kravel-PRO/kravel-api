package com.kravel.server.model.member;

import com.kravel.server.common.util.exception.NotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    QMember member = QMember.member;

    public Optional<Member> findMemberByLoginEmail(String loginEmail) throws NotFoundException {
        return Optional.ofNullable(queryFactory.selectFrom(member)
                .where(member.loginEmail.eq(loginEmail)).fetchOne());
    }
}
