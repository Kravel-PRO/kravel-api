package com.kravel.server.model.mapping;

import com.kravel.server.model.review.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewLikeQueryRepository {

    private final JPAQueryFactory queryFactory;

    QReviewLike reviewLike = QReviewLike.reviewLike;

    public Optional<ReviewLike> checkReviewLikeExist(long reviewId, long memberId) {
        return Optional.ofNullable(queryFactory.selectFrom(reviewLike).where(reviewLike.review.id.eq(reviewId).and(reviewLike.member.id.eq(memberId))).distinct().fetchOne());
    }

    public long findLikeCountByReview(long reviewId) {
        return queryFactory.selectFrom(reviewLike).where(reviewLike.review.id.eq(reviewId)).distinct().fetchCount();
    }
}
