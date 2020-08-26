package com.kravel.server.model.place;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaceQueryRepository {

    private final JPAQueryFactory queryFactory;
//
//
//    public List<Place> findAllPlacesByLocation(PlaceSearchRequestDTO placeSearchRequestDTO) throws Exception {
//        QPlace place = QPlace.place;
//        QPlaceInfo placeInfo = QPlaceInfo.placeInfo;
//        QMedia media = QMedia.media;
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
