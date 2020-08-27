package com.kravel.server.model.place;

import com.kravel.server.model.BaseEntity;
import com.kravel.server.model.mapping.PlaceCelebrity;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Place extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "place_id")
    private long id;

    private String title;
    private String location;
    private String latitude;
    private String longitude;
    private double grade;
    private double weight;
    private String imageUrl;
    private String useAt = "Y";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceInfo> placeInfos = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<PlaceCelebrity> placeCelebrities = new ArrayList<>();

    @Builder
    public Place(String latitude, String longitude, double grade, double weight, String imageUrl) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.grade = grade;
        this.weight = weight;
        this.imageUrl = imageUrl;
    }

    public Optional<PlaceInfo> findPlaceInfoByLangu(String langu) {
        return placeInfos.stream().filter(info -> info.getSpeech().equals(langu)).findFirst();
    }
}
