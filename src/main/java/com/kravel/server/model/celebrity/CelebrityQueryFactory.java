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

    QCelebrity celebrity = QCelebrity.celebrity;

    public Page<Celebrity> finaAllCelebrity(String search, Pageable pageable) {
        return (Page<Celebrity>) queryFactory.selectFrom(celebrity)
                .where(celebrity.name.eq("%" + search + "5"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
