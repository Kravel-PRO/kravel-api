package com.kravel.server.model.place;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.dto.update.place.EngPlaceInfoUpdateDTO;
import com.kravel.server.dto.update.place.KorPlaceInfoUpdateDTO;
import com.kravel.server.dto.update.place.PlaceUpdateDTO;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.BaseEntity;
import com.kravel.server.model.mapping.PlaceCelebrity;
import com.kravel.server.model.mapping.PlaceCelebrityRepository;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaRepository;
import com.kravel.server.model.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
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

    @Column(length = 5)
    private String useAt = "Y";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceInfo> placeInfos = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceCelebrity> placeCelebrities = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Place(long id, String bus, String subway, double latitude, double longitude, String imageUrl, String subImageUrl, String filterImageUrl, String useAt, Media media, List<PlaceInfo> placeInfos, List<Review> reviews, List<Scrap> scraps, List<PlaceCelebrity> placeCelebrities, List<Tag> tags) {
        this.id = id;
        this.bus = bus;
        this.subway = subway;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.subImageUrl = subImageUrl;
        this.filterImageUrl = filterImageUrl;
        this.useAt = useAt;
        this.media = media;
        this.placeInfos = placeInfos;
        this.reviews = reviews;
        this.scraps = scraps;
        this.placeCelebrities = placeCelebrities;
        this.tags = tags;
    }

    public void changeBus(String bus) {
        this.bus = bus;
    }
    public void changeSubway(String subway) {
        this.subway = subway;
    }
    public void changeLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void changeLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void changePlaceInfo(List<PlaceInfo> placeInfos) {
        this.placeInfos = placeInfos;
    }
    public void changeMedia(Media media) {
        this.media = media;
    }
    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void changeSubImageUrl(String subImageUrl) {
        this.subImageUrl = subImageUrl;
    }
    public void changeFilterImageUrl(String filterImageUrl) {
        this.filterImageUrl = filterImageUrl;
    }
    public void changePlaceCelebrities(List<PlaceCelebrity> placeCelebrities) {
        this.placeCelebrities = placeCelebrities;
    }
    public void changeTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void findTagSpeech(Speech speech) {
        this.tags = Optional.ofNullable(this.tags).orElse(new ArrayList<>()).stream()
                .filter(tag -> tag.getSpeech().equals(speech))
                .collect(Collectors.toList());
    }

    public void findInfoSpeech(Speech speech) {
        this.placeInfos = Optional.ofNullable(this.placeInfos).orElse(new ArrayList<>()).stream()
                .filter(info -> info.getSpeech().equals(speech))
                .collect(Collectors.toList());
    }
}
