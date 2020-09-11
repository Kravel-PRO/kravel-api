package com.kravel.server.model.place;

import com.kravel.server.common.OrderUtil;
import com.kravel.server.model.celebrity.QCelebrity;
import com.kravel.server.model.celebrity.QCelebrityInfo;
import com.kravel.server.model.mapping.QPlaceCelebrity;
import com.kravel.server.model.mapping.QScrap;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.media.QMedia;
import com.kravel.server.model.media.QMediaInfo;
import com.kravel.server.model.review.QReview;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaceQueryRepository {

    private final JPAQueryFactory queryFactory;

    QPlace place = QPlace.place;
    QPlaceInfo placeInfo = QPlaceInfo.placeInfo;
    QPlaceCelebrity placeCelebrity = QPlaceCelebrity.placeCelebrity;
    QCelebrity celebrity = QCelebrity.celebrity;
    QMedia media = QMedia.media;
    QScrap scrap = QScrap.scrap;
    QReview review = QReview.review;
    QTag tag = QTag.tag;
    QCelebrityInfo celebrityInfo = QCelebrityInfo.celebrityInfo;
    QMediaInfo mediaInfo = QMediaInfo.mediaInfo;

    public Optional<Place> findById(long placeId, String speech) {
        return Optional.ofNullable(queryFactory.selectFrom(place)
                .innerJoin(place.placeInfos, placeInfo)
                    .fetchJoin()
//                .leftJoin(place.placeCelebrities, placeCelebrity)
//                    .fetchJoin()
//                .leftJoin(placeCelebrity.celebrity, celebrity)
//                    .fetchJoin()
//                .leftJoin(celebrity.celebrityInfos, celebrityInfo)
//                    .on(celebrityInfo.speech.eq(speech))
//                    .fetchJoin()
//                .leftJoin(place.media, media)
//                    .fetchJoin()
//                .leftJoin(media.mediaInfos, mediaInfo)
//                    .on(mediaInfo.speech.eq(speech))
//                    .fetchJoin()
                .where(place.id.eq(placeId))
                .fetchOne());
    }

    public List<Place> findAllByMedia(long mediaId, String speech, Pageable pageable) {
        return queryFactory.selectFrom(place)
                .leftJoin(place.placeInfos, placeInfo).fetchJoin()
                .leftJoin(place.tags, tag)
                .where(
                        place.media.id.eq(mediaId),
                        placeInfo.speech.eq(speech),
                        tag.speech.eq(speech)
                        )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Place> findAllByCelebrity(long celebrityId, String speech) {
        return queryFactory.selectFrom(place)
                .leftJoin(place.placeInfos, placeInfo).fetchJoin()
                .innerJoin(place.placeCelebrities, placeCelebrity)
                .innerJoin(placeCelebrity.celebrity, celebrity)
                .where(
                        placeInfo.speech.eq(speech),
                        celebrity.id.eq(celebrityId)
                ).fetch();
    }

    public List<Place> findMapByLocation(double latitude, double longitude, double height, double width) throws Exception {
        return queryFactory.selectFrom(place)
                .innerJoin(place.placeInfos, placeInfo).fetchJoin()
                .leftJoin(place.reviews, review)
                .where(
                        latitudeBetween(latitude, height),
                        longitudeBetween(longitude, width)
                )
                .fetch();
    }

    public Page<Place> findAllByLocation(double latitude, double longitude, double height, double width, String speech, Pageable pageable) throws Exception {
        List<Place> places = queryFactory.selectFrom(place)
                .innerJoin(place.placeInfos, placeInfo).fetchJoin()
                .leftJoin(place.reviews, review)
                .where(
                        placeInfo.speech.eq(speech),
                        latitudeBetween(latitude, height),
                        longitudeBetween(longitude, width)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(OrderUtil.byLongitude(longitude, pageable, "place"), OrderUtil.byLatitude(latitude, pageable, "place"))
//                .orderBy(OrderUtil.sort(pageable, "place"))
                .fetch();

        long placeCount = queryFactory.selectFrom(place)
                .innerJoin(place.placeInfos, placeInfo).fetchJoin()
                .leftJoin(place.reviews, review)
                .where(
                        placeInfo.speech.eq(speech),
                        latitudeBetween(latitude, height),
                        longitudeBetween(longitude, width)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(places, pageable, placeCount);
    }

    private BooleanExpression latitudeBetween(double latitude, double height) {
        return latitude != 0 ? place.latitude.between(latitude - height, latitude + height) : null;
    }

    private BooleanExpression longitudeBetween(double longitude, double width) {
        return longitude != 0 ? place.longitude.between(longitude - width, longitude + width) : null;
    }

    public boolean checkScrapState(long placeId, long memberId) throws Exception {
        return Optional.ofNullable(queryFactory.selectFrom(scrap)
                .where(scrap.member.id.eq(memberId)
                        .and(scrap.place.id.eq(placeId)))
                .fetchOne()).isPresent();
    }

    public Optional<Scrap> findScrapByPlaceAndMember(long placeId, long memberId) throws Exception {
        return Optional.ofNullable(queryFactory.selectFrom(scrap)
                .where(scrap.member.id.eq(memberId)
                        .and(scrap.place.id.eq(placeId)))
                .fetchOne());
    }
}
