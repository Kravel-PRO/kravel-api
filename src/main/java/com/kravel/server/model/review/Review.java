package com.kravel.server.model.review;

import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.mapping.ReviewLike;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.place.Place;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private long id;

    private float grade;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @ManyToOne
    @JoinColumn(name = "celebrity_id")
    private Celebrity celebrity;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes;
}
