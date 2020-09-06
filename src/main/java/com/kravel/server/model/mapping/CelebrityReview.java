package com.kravel.server.model.mapping;

import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CelebrityReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "celebrity_review_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "celebrity_id")
    private Celebrity celebrity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public CelebrityReview(Celebrity celebrity, Review review) {
        this.celebrity = celebrity;
        this.review = review;
    }
}
