package com.kravel.server.model.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    QReview review = QReview.review;

    public List<Review> findAllByPlace(long placeId, Pageable pageable) throws Exception {
        return queryFactory.selectFrom(review)
                .where(review.place.id.eq(placeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public long findCountByPlace(long placeId) throws Exception {
        return queryFactory.selectFrom(review)
                .where(review.place.id.eq(placeId))
                .fetchCount();
    }

    public List<Review> findAllReviewByCelebrity(long celebrityId) throws Exception {
        return queryFactory.selectFrom(review)
                .where(review.celebrity.id.eq(celebrityId))
                .fetch();
    }
}
