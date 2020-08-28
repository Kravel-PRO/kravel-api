package com.kravel.server.model.member;

import com.kravel.server.common.util.exception.NotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    QMember member = QMember.member;

    public Member findMemberByLoginEmail(String loginEmail) throws NotFoundException {
        return queryFactory.selectFrom(member)
                .where(member.loginEmail.eq(loginEmail)).fetchOne();
    }
}
