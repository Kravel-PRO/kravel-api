package com.kravel.server.model.place;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PhotoFilterQueryRepository {

    private final JPAQueryFactory queryFactory;

    QPlace place = QPlace.place;
    QPhotoFilter photoFilter = QPhotoFilter.photoFilter;

    public PhotoFilter findPhotoFilterByPlace(long placeId) {
        return queryFactory.selectFrom(photoFilter)
                .innerJoin(photoFilter.place, place).fetchJoin()
                .where(photoFilter.place.id.eq(placeId))
                .fetchFirst();
    }
}
