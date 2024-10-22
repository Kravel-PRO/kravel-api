package com.kravel.server.model.celebrity;

import com.kravel.server.common.OrderUtil;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.mapping.QCelebrityMedia;
import com.kravel.server.model.media.QMedia;
import com.kravel.server.model.media.QMediaInfo;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CelebrityQueryRepository {

    private final JPAQueryFactory queryFactory;

    QCelebrity celebrity = QCelebrity.celebrity;
    QCelebrityInfo celebrityInfo = QCelebrityInfo.celebrityInfo;

    public Page<Celebrity> findAll(Pageable pageable, Speech speech) {
        QueryResults<Celebrity> results = queryFactory.selectFrom(celebrity)
                .innerJoin(celebrity.celebrityInfos, celebrityInfo).fetchJoin()
                .where(celebrityInfo.speech.eq(speech))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(OrderUtil.sort(pageable, "celebrity"))
                .distinct()
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }


}
