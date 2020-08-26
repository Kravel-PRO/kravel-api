package com.kravel.server.model.media;

import com.kravel.bella.dto.place.PlaceSearchRequestDTO;
import com.kravel.bella.model.celebrity.QCelebrity;
import com.kravel.bella.model.mapping.QCelebrityMedia;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MediaQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<Media> findAll(PlaceSearchRequestDTO placeSearchRequestDTO, Pageable pageable) throws Exception {

        QMedia media = QMedia.media;
        QCelebrityMedia celebrityMedia = QCelebrityMedia.celebrityMedia;
        QCelebrity celebrity = QCelebrity.celebrity;

        return (Page<Media>) queryFactory.selectFrom(media)
                .leftJoin(media.celebrityMedias, celebrityMedia).fetchJoin()
                .leftJoin(celebrityMedia.celebrity, celebrity).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
