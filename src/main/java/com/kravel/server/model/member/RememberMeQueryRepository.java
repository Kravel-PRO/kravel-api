package com.kravel.server.model.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RememberMeQueryRepository {

    private final JPAQueryFactory queryFactory;

    QRememberMe rememberMe = QRememberMe.rememberMe;
    QMember member = QMember.member;

    public Optional<RememberMe> findByMember(long memberId) {
        return Optional.ofNullable(queryFactory.selectFrom(rememberMe)
                .innerJoin(rememberMe.member, member)
                    .fetchJoin()
                .where(rememberMe.member.id.eq(memberId))
                .distinct()
                .fetchOne());
    }
}
