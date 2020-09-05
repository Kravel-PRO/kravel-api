package com.kravel.server.model.celebrity;

import com.kravel.server.model.mapping.QCelebrityMedia;
import com.kravel.server.model.media.QMedia;
import com.kravel.server.model.media.QMediaInfo;
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

    public List<Celebrity> finaAllCelebrity(Pageable pageable) {
        return queryFactory.selectFrom(celebrity)
                .innerJoin(celebrity.celebrityInfos, celebrityInfo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


}
