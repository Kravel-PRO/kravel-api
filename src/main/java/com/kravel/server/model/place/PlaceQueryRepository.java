package com.kravel.server.model.place;

import com.kravel.server.model.celebrity.QCelebrity;
import com.kravel.server.model.mapping.QPlaceCelebrity;
import com.kravel.server.model.mapping.QScrap;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.media.QMedia;
import com.kravel.server.model.review.QReview;
import com.querydsl.core.QueryResults;
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
    QPhotoFilter photoFilter = QPhotoFilter.photoFilter;

    public Optional<Place> findById(long placeId, String speech) {
        return Optional.ofNullable(queryFactory.selectFrom(place)
                // TODO: fetch join을 했는데도 쿼리가 한번 더 날아가서 ENG, KOR 모든 언어 데이터를 가져온다.
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
                .leftJoin(place.photoFilter, photoFilter)
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
                .where(placeInfo.speech.eq(speech).and(celebrity.id.eq(celebrityId))).fetch();
    }


    public Page<Place> findAll(String speech, Pageable pageable) throws Exception {
        QueryResults<Place> placeQueryResults = queryFactory.selectFrom(place)
                .innerJoin(placeInfo)
                    .on(place.id.eq(placeInfo.id)
                    .and(placeInfo.speech.eq(speech)))
                    .fetchJoin()

                .leftJoin(place.reviews, review)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(review.count().desc())
                .fetchResults();

        return new PageImpl<>(placeQueryResults.getResults(), pageable, placeQueryResults.getTotal());
    }

    public Page<Place> findAllByLocation(double latitude, double longitude, double height, double width, String speech, Pageable pageable) throws Exception {

        QueryResults<Place> placeQueryResults = queryFactory.selectFrom(place)
                .where(place.latitude.between(
                        String.valueOf(latitude - height),
                        String.valueOf(latitude + height))

                        .and(place.latitude.between(
                                String.valueOf(longitude - width),
                                String.valueOf(longitude + width))))
                .innerJoin(placeInfo)
                    .on(placeInfo.id.eq(place.id)
                            .and(placeInfo.speech.eq(speech)))
                    .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(placeQueryResults.getResults(), pageable, placeQueryResults.getTotal());
    }

    public Optional<Scrap> checkScrapState(long placeId, long memberId) throws Exception {
        return Optional.ofNullable(queryFactory.selectFrom(scrap)
                .where(scrap.member.id.eq(memberId)
                        .and(scrap.place.id.eq(placeId)))
                .fetchOne());
    }
}
