package com.kravel.server.model.celebrity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CelebrityQueryFactory {

    private final JPAQueryFactory queryFactory;

    QCelebrity celebrity = QCelebrity.celebrity;
    QCelebrityInfo celebrityInfo = QCelebrityInfo.celebrityInfo;

    public List<Celebrity> finaAllCelebrity(String search, Pageable pageable) {
        return queryFactory.selectFrom(celebrity)
                .innerJoin(celebrity.celebrityInfos, celebrityInfo)
                .where(celebrityInfo.name.like("%" + search + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
