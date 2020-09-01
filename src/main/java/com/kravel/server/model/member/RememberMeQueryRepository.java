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

    public Optional<RememberMe> findByLoginEmail(String loginEmail) {
        return Optional.ofNullable(queryFactory.selectFrom(rememberMe)
                .where(rememberMe.loginEmail.eq(loginEmail))
                .fetchOne());
    }
}
