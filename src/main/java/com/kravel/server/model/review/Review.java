package com.kravel.server.model.review;

import com.kravel.bella.model.BaseTimeEntity;
import com.kravel.bella.model.celebrity.Celebrity;
import com.kravel.bella.model.mapping.ReviewLike;
import com.kravel.bella.model.media.Media;
import com.kravel.bella.model.member.Member;
import com.kravel.bella.model.place.Place;
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
