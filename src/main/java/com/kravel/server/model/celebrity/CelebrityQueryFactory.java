package com.kravel.server.model.celebrity;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CelebrityQueryFactory {

    private final JPAQueryFactory queryFactory;

    QCelebrity celebrity = QCelebrity.celebrity;

    public List<Celebrity> finaAllCelebrity(String search, Pageable pageable) {
        return queryFactory.selectFrom(celebrity)
                .where(celebrity.name.like("%" + search + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
