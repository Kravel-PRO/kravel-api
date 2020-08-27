package com.kravel.server.model.mapping;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewLikeQueryRepository {

    private final JPAQueryFactory queryFactory;

    QReviewLike reviewLike = QReviewLike.reviewLike;

    public boolean checkReviewLikeExist(long reviewId, long memberId) {
        return queryFactory.selectFrom(reviewLike).where(reviewLike.review.id.eq(reviewId).and(reviewLike.member.id.eq(memberId))).fetchCount() != 0;
    }

    public long findReviewLikeCount(long reviewId) {
        return queryFactory.selectFrom(reviewLike).where(reviewLike.review.id.eq(reviewId)).fetchCount();
    }
}
