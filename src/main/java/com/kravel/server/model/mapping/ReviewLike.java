package com.kravel.server.model.mapping;

import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
public class ReviewLike extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "review_like_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public ReviewLike(long id, Member member, Review review) {
        this.id = id;
        this.member = member;
        this.review = review;
    }
}
