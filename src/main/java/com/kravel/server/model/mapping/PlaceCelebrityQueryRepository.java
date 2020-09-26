package com.kravel.server.model.mapping;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaceCelebrityQueryRepository {

    private final JPQLQueryFactory queryFactory;

    QPlaceCelebrity placeCelebrity = QPlaceCelebrity.placeCelebrity;

    public List<PlaceCelebrity> findByPlace(long placeId) {
        return queryFactory.selectFrom(placeCelebrity)
                .where(placeCelebrity.place.id.eq(placeId))
                .fetch();
    }
}
