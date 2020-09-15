package com.kravel.server.model.celebrity;

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
public class CelebrityQueryFactory {

    private final JPAQueryFactory queryFactory;

    QCelebrity celebrity = QCelebrity.celebrity;
    QCelebrityInfo celebrityInfo = QCelebrityInfo.celebrityInfo;

    public Page<Celebrity> finaAllCelebrity(Pageable pageable) {
        QueryResults<Celebrity> results = queryFactory.selectFrom(celebrity)
                .innerJoin(celebrity.celebrityInfos, celebrityInfo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(
                results.getResults().stream().distinct().collect(Collectors.toList()),
                pageable,
                results.getTotal()
        );
    }


}
