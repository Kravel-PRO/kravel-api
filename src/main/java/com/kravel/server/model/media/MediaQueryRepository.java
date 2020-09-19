package com.kravel.server.model.media;

import com.kravel.server.common.OrderUtil;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.QPlace;
import com.kravel.server.model.place.QPlaceInfo;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.kravel.server.model.place.QPlaceInfo.placeInfo;

@Repository
@RequiredArgsConstructor
public class MediaQueryRepository {

    private final JPAQueryFactory queryFactory;

    QMedia media = QMedia.media;
    QMediaInfo mediaInfo = QMediaInfo.mediaInfo;
    QPlace place = QPlace.place;
    QPlaceInfo placeInfo = QPlaceInfo.placeInfo;

    public Optional<Media> findById(long mediaId, Speech speech) {
        Media media = queryFactory.selectFrom(this.media)
                .innerJoin(this.media.mediaInfos, mediaInfo)
                .on(mediaInfo.speech.eq(speech))
                .where(this.media.id.eq(mediaId)).fetchOne();
        return Optional.ofNullable(media);
    }

    public List<Place> findAllByMedia(long mediaId, Speech speech, Pageable pageable) {
        return queryFactory.selectFrom(place)
                .leftJoin(place.placeInfos, placeInfo).fetchJoin()
                .innerJoin(place.media, media)
                .where(
                        placeInfo.speech.eq(speech),
                        media.id.eq(mediaId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public Page<Media> findAll(Pageable pageable, Speech speech) {
        QueryResults<Media> queryResults = queryFactory.selectFrom(this.media)
                .innerJoin(this.media.mediaInfos, mediaInfo).fetchJoin()
                .where(mediaInfo.speech.eq(speech))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(OrderUtil.sort(pageable, "media"))
                .distinct()
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());


    }
}
