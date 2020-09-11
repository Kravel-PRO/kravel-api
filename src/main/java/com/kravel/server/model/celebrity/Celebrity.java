package com.kravel.server.model.celebrity;

import com.kravel.server.model.BaseEntity;
import com.kravel.server.model.mapping.CelebrityMedia;
import com.kravel.server.model.mapping.CelebrityReview;
import com.kravel.server.model.mapping.PlaceCelebrity;
import com.kravel.server.model.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Celebrity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "celebrity_id")
    private long id;

    @Lob
    private String imageUrl;

    @OneToMany(mappedBy = "celebrity", cascade = CascadeType.ALL)
    private List<CelebrityInfo> celebrityInfos = new ArrayList<>();

    @OneToMany(mappedBy = "celebrity", cascade = CascadeType.ALL)
    private List<CelebrityMedia> celebrityMedias = new ArrayList<>();

    @OneToMany(mappedBy = "celebrity", cascade = CascadeType.ALL)
    private List<CelebrityReview> celebrityReviews = new ArrayList<>();

    @OneToMany(mappedBy = "celebrity", cascade = CascadeType.ALL)
    private List<PlaceCelebrity> placeCelebrities = new ArrayList<>();

    @Builder
    public Celebrity(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void findInfoSpeech(String speech) {
        this.celebrityInfos = Optional.ofNullable(this.celebrityInfos).orElse(new ArrayList<>()).stream()
                .filter(info -> info.getSpeech().equals(speech))
                .collect(Collectors.toList());
    }
}
