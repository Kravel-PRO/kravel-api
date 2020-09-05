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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private long id;

    private String bus;
    private String subway;
    private double latitude;
    private double longitude;

    @Lob
    private String imageUrl;

    @Lob
    private String subImageUrl;

    @Lob
    private String filterImageUrl;

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

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Place(double latitude, double longitude, String imageUrl, String subImageUrl, String filterImageUrl) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.subImageUrl = subImageUrl;
        this.filterImageUrl = filterImageUrl;
    }

    public Place(PlaceUpdateDTO placeUpdateDTO) {
        this.bus = placeUpdateDTO.getBus();
        this.subway = placeUpdateDTO.getSubway();
        this.latitude = placeUpdateDTO.getLatitude();
        this.longitude = placeUpdateDTO.getLongitude();
        this.imageUrl = placeUpdateDTO.getImageUrl();
        this.subImageUrl = placeUpdateDTO.getSubImageUrl();
        this.placeInfos = placeUpdateDTO.getPlaceInfos().stream().map(PlaceInfo::new).collect(Collectors.toList());
        this.tags = placeUpdateDTO.getTags().stream().map(Tag::new).collect(Collectors.toList());
        this.filterImageUrl = placeUpdateDTO.getFilterImageUrl();
    }
}
