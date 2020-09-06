package com.kravel.server.model.review;

import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.model.celebrity.QCelebrity;
import com.kravel.server.model.mapping.QCelebrityReview;
import com.kravel.server.model.mapping.QReviewLike;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.QMedia;
import com.kravel.server.model.member.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    QReview review = QReview.review;
    QMember member = QMember.member;
    QReviewLike reviewLike = QReviewLike.reviewLike;
    QCelebrity celebrity = QCelebrity.celebrity;
    QCelebrityReview celebrityReview = QCelebrityReview.celebrityReview;

    public Page<Review> findAllByPlace(long placeId, Pageable pageable) throws Exception {
        QueryResults<Review> reviewQueryResults = queryFactory.selectFrom(review)
                .where(review.place.id.eq(placeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(reviewQueryResults.getResults(), pageable, reviewQueryResults.getTotal());
    }

    public long findCountByPlace(long placeId) throws Exception {
        return queryFactory.selectFrom(review)
                .where(review.place.id.eq(placeId))
                .fetchCount();
    }

    public Page<Review> findAllReviewByCelebrity(long celebrityId, Pageable pageable) throws Exception {
        QueryResults<Review> reviewQueryResults = queryFactory.selectFrom(review)
                .innerJoin(review.celebrityReviews, celebrityReview)
                .innerJoin(celebrityReview.celebrity, celebrity)
                .where(celebrity.id.eq(celebrityId))
                .fetchResults();

        return new PageImpl<>(reviewQueryResults.getResults(), pageable, reviewQueryResults.getTotal());
    }

    public Page<Review> findAllByMedia(long mediaId, Pageable pageable) throws Exception {
        QueryResults<Review> queryResults = queryFactory.selectFrom(review)
                .where(review.media.id.eq(mediaId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    public Page<Review> findAllByMember(long memberId, Pageable pageable) {
        QueryResults<Review> queryResults = queryFactory.selectFrom(review)
                .innerJoin(review.member, member)
                .where(member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    public Page<Review> findAllReviewLikeById(long memberId, Pageable pageable) {
        QueryResults<Review> reviewQueryResults = queryFactory.selectFrom(review)
                .innerJoin(review.reviewLikes, reviewLike)
                .innerJoin(reviewLike.member, member)
                .where(member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(reviewQueryResults.getResults(), pageable, reviewQueryResults.getTotal());
    }
}
