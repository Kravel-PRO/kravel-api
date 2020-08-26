package com.kravel.server.model.celebrity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CelebrityQueryFactory {

    private final JPAQueryFactory queryFactory;
//
//    public Page<Celebrity> finaAllCelebrity(String search, Pageable pageable) {
//        return queryFactory.selectFrom()
//    }
}
