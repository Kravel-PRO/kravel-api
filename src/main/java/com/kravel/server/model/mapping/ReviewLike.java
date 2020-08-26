package com.kravel.server.model.mapping;

import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.review.Review;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class ReviewLike extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "review_like_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
