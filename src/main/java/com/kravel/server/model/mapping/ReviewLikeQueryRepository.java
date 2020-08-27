package com.kravel.server.model.mapping;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewLikeQueryRepository {

    private final JPAQueryFactory queryFactory;

    QReviewLike reviewLike = QReviewLike.reviewLike;

    public ReviewLike checkReviewLikeExist(long reviewId, long memberId) {
        return queryFactory.selectFrom(reviewLike).where(reviewLike.review.id.eq(reviewId).and(reviewLike.member.id.eq(memberId))).fetchOne();
    }

    public long findReviewLikeCountById(long reviewId) {
        return queryFactory.selectFrom(reviewLike).where(reviewLike.review.id.eq(reviewId)).fetchCount();
    }

    public long findReviewLikeCountByCelebrity(long celebrityId) {
        return queryFactory.selectFrom(reviewLike).where(reviewLike.review.celebrity.id.eq(celebrityId)).fetchCount();
    }
}
