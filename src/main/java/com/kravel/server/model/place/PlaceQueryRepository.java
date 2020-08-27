package com.kravel.server.model.place;

import com.kravel.server.model.celebrity.QCelebrity;
import com.kravel.server.model.mapping.QPlaceCelebrity;
import com.kravel.server.model.media.QMedia;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    public List<Place> findAllByMedia(long mediaId) {
        return queryFactory.selectFrom(place)
                .leftJoin(place.placeInfos, placeInfo)
                .where(place.media.id.eq(mediaId))
                .fetch();
    }
//
//
//    public List<Place> findAllPlacesByLocation(PlaceSearchRequestDTO placeSearchRequestDTO) throws Exception {

//
//        return (List<Place>) queryFactory.selectFrom(place)
//                .where(place.latitude.between(
//                        String.valueOf(placeSearchRequestDTO.getLatitude() - placeSearchRequestDTO.getHeight()),
//                        String.valueOf(placeSearchRequestDTO.getLatitude() + placeSearchRequestDTO.getHeight()))
//
//                        .and(place.latitude.between(
//                                String.valueOf(placeSearchRequestDTO.getLongitude() - placeSearchRequestDTO.getWidth()),
//                                String.valueOf(placeSearchRequestDTO.getLongitude() + placeSearchRequestDTO.getWidth()))))
//                .innerJoin(place.placeInfos, placeInfo).fetchJoin()
//                .leftJoin(place.media, media).fetchJoin()
//                .fetch();
//    }
}
