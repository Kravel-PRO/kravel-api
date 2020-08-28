package com.kravel.server.model.place;

import com.kravel.server.model.celebrity.QCelebrity;
import com.kravel.server.model.mapping.QPlaceCelebrity;
import com.kravel.server.model.mapping.QScrap;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.media.QMedia;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Place> findAllPlacesByLocation(double latitude, double longitude, double height, double width, String speech, Pageable pageable) throws Exception {

        return (List<Place>) queryFactory.selectFrom(place)
                .where(place.latitude.between(
                        String.valueOf(latitude - height),
                        String.valueOf(latitude + height))

                        .and(place.latitude.between(
                                String.valueOf(longitude - width),
                                String.valueOf(longitude + width))))
                .innerJoin(place.placeInfos, placeInfo).fetchJoin()
                .leftJoin(place.media, media).fetchJoin()
                .fetch();
    }

    public Scrap checkScrapState(long placeId, long memberId) throws Exception {
        return queryFactory.selectFrom(scrap)
                .where(scrap.member.id.eq(memberId)
                        .and(scrap.place.id.eq(placeId)))
                .fetchOne();
    }
}
