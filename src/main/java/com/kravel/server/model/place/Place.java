package com.kravel.server.model.place;

import com.kravel.server.dto.update.PlaceUpdateDTO;
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
import java.util.stream.Collectors;

@Entity @Getter
@NoArgsConstructor
public class Place extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "place_id")
    private long id;

    private String location;
    private String bus;
    private String subway;
    private String latitude;
    private String longitude;
    private double grade = 0;
    private double weight = 0;
    private String imageUrl;
    private String subImageUrl;
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

    @OneToOne(mappedBy = "place")
    private PhotoFilter photoFilter;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Place(String latitude, String longitude, double grade, double weight, String imageUrl, PhotoFilter photoFilter, String subImageUrl) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.grade = grade;
        this.weight = weight;
        this.imageUrl = imageUrl;
        this.photoFilter = photoFilter;
        this.subImageUrl = subImageUrl;
    }

    public Place(PlaceUpdateDTO placeUpdateDTO) {
        this.location = placeUpdateDTO.getLocation();
        this.bus = placeUpdateDTO.getBus();
        this.subway = placeUpdateDTO.getSubway();
        this.latitude = placeUpdateDTO.getLatitude();
        this.longitude = placeUpdateDTO.getLongitude();
        this.imageUrl = placeUpdateDTO.getImageUrl();
        this.subImageUrl = placeUpdateDTO.getSubImageUrl();
        this.placeInfos = placeUpdateDTO.getPlaceInfos().stream().map(PlaceInfo::new).collect(Collectors.toList());
        this.photoFilter = Optional.of(placeUpdateDTO.getPhotoFilter()).map(PhotoFilter::new).orElse(new PhotoFilter());
        this.tags = placeUpdateDTO.getTags().stream().map(Tag::new).collect(Collectors.toList());
    }
}
