package com.kravel.server.model.place;

import com.kravel.server.model.celebrity.QCelebrity;
import com.kravel.server.model.mapping.QPlaceCelebrity;
import com.kravel.server.model.mapping.QScrap;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.media.QMedia;
import com.kravel.server.model.review.QReview;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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

    public Optional<Place> findById(long placeId, String speech) {
        return Optional.ofNullable(queryFactory.selectFrom(place)
                .innerJoin(placeInfo)
                    .on(placeInfo.place.id.eq(placeId)
                    .and(placeInfo.speech.eq(speech)))
                    .fetchJoin()
                .leftJoin(place.placeCelebrities, placeCelebrity)
                    .fetchJoin()
                .leftJoin(placeCelebrity.celebrity, celebrity)
                    .fetchJoin()
                .leftJoin(place.media, media)
                    .fetchJoin()
                .where(place.id.eq(placeId))
                .fetchOne());
    }

    public List<Place> findAllByMedia(long mediaId) {
        return queryFactory.selectFrom(place)
                .leftJoin(place.placeInfos, placeInfo)
                .where(place.media.id.eq(mediaId))
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


//    public Page<Place> findAll(String speech, Pageable pageable) throws Exception {
//        QueryResults<Place> placeQueryResults = queryFactory.selectFrom(place)
//                .innerJoin(place.placeInfos, placeInfo).fetchJoin()
//                .leftJoin(place.reviews, review)
//
//                .where(placeInfo.speech.eq(speech))
//
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//
//                .orderBy(review.count().desc())
//                .fetchResults();
//
//        return new PageImpl<>(placeQueryResults.getResults(), pageable, placeQueryResults.getTotal());
//    }

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
//                .orderBy(review.count().desc())
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
//                .orderBy(review.count().desc())
                .fetchCount();

        return new PageImpl<>(places, pageable, placeCount);
    }

    private BooleanExpression latitudeBetween(double latitude, double height) {
        return latitude != 0 ? place.latitude.between(latitude - height, latitude + height) : null;
    }

    private BooleanExpression longitudeBetween(double longitude, double width) {
        return longitude != 0 ? place.longitude.between(longitude - width, longitude + width) : null;
    }

    public Optional<Scrap> checkScrapState(long placeId, long memberId) throws Exception {
        return Optional.ofNullable(queryFactory.selectFrom(scrap)
                .where(scrap.member.id.eq(memberId)
                        .and(scrap.place.id.eq(placeId)))
                .fetchOne());
    }


}
