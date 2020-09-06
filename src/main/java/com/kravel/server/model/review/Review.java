package com.kravel.server.model.review;

import com.kravel.server.common.S3Uploader;
import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.mapping.CelebrityReview;
import com.kravel.server.model.mapping.ReviewLike;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.member.Member;
import com.kravel.server.model.place.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity @Getter
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long id;

    @Lob
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<CelebrityReview> celebrityReviews = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes;

    public void saveImage(S3Uploader s3Uploader, MultipartFile file) throws IOException {
        this.imageUrl = s3Uploader.upload(file, "review");
    }

    public void changeMedia(Media media) {
        this.media = media;
    }

    @Builder
    public Review(long id, Place place, Member member, Media media, List<Celebrity> celebrities, List<ReviewLike> reviewLikes) {
        this.id = id;
        this.place = place;
        this.member = member;
        this.media = media;
        this.celebrityReviews = celebrities.stream().map(celebrity -> new CelebrityReview(celebrity, this)).collect(Collectors.toList());
        this.reviewLikes = reviewLikes;
    }

}
