package com.kravel.server.model.mapping;

import com.kravel.server.enums.Speech;
import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.celebrity.QCelebrity;
import com.kravel.server.model.celebrity.QCelebrityInfo;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.QMedia;
import com.kravel.server.model.media.QMediaInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CelebrityMediaQueryRepository {

    private final JPAQueryFactory queryFactory;

    QMedia media = QMedia.media;
    QCelebrity celebrity = QCelebrity.celebrity;
    QMediaInfo mediaInfo = QMediaInfo.mediaInfo;
    QCelebrityInfo celebrityInfo = QCelebrityInfo.celebrityInfo;

    public List<Celebrity> findCelebrityBySearch(String search, Speech speech, Pageable pageable) {

        return queryFactory.selectFrom(celebrity)
                .innerJoin(celebrity.celebrityInfos, celebrityInfo).fetchJoin()
                .where(
                        celebrityInfo.speech.eq(speech),
                        celebrityInfo.name.like("%" + search + "%")
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch().stream().distinct().collect(Collectors.toList());
    }

    public List<Media> findMediaBySearch(String search, Speech speech, Pageable pageable) {
        return queryFactory.selectFrom(media)
                .innerJoin(media.mediaInfos, mediaInfo).fetchJoin()
                .where(
                        mediaInfo.speech.eq(speech),
                        mediaInfo.title.like("%" + search + "%")
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch().stream().distinct().collect(Collectors.toList());
    }
}
