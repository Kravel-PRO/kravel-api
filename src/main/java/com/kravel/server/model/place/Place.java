package com.kravel.server.model.place;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.dto.update.EngPlaceInfoUpdateDTO;
import com.kravel.server.dto.update.KorPlaceInfoUpdateDTO;
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
    public Place(long id, double latitude, double longitude, String imageUrl, String subImageUrl, String filterImageUrl) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.subImageUrl = subImageUrl;
        this.filterImageUrl = filterImageUrl;
    }

    public Place(PlaceUpdateDTO placeUpdateDTO, S3Uploader s3Uploader, ObjectMapper objectMapper) throws IOException {
        this.bus = placeUpdateDTO.getBus();
        this.subway = placeUpdateDTO.getSubway();
        this.latitude = placeUpdateDTO.getLatitude();
        this.longitude = placeUpdateDTO.getLongitude();

        KorPlaceInfoUpdateDTO korPlaceInfoUpdateDTO = objectMapper.readValue(placeUpdateDTO.getKorInfo(), KorPlaceInfoUpdateDTO.class);
        EngPlaceInfoUpdateDTO engPlaceInfoUpdateDTO = objectMapper.readValue(placeUpdateDTO.getEngInfo(), EngPlaceInfoUpdateDTO.class);
        List<Integer> celebrities = objectMapper.readValue(placeUpdateDTO.getCelebrities(), new TypeReference<List<Integer >>(){});

        System.out.println(celebrities.toString());

        this.placeInfos.add(new PlaceInfo(korPlaceInfoUpdateDTO));
        this.placeInfos.add(new PlaceInfo(engPlaceInfoUpdateDTO));
        this.tags.add(new Tag(this, korPlaceInfoUpdateDTO));
        this.tags.add(new Tag(this, engPlaceInfoUpdateDTO));

        if (celebrities.size() != 0) {
            celebrities.forEach(celebrity -> {
                PlaceCelebrity placeCelebrity = new PlaceCelebrity(this, celebrity);
                this.placeCelebrities.add(placeCelebrity);
            });
        }
        if (placeUpdateDTO.getMedia() != 0) {
            this.media = new Media(placeUpdateDTO.getMedia());
        }

        this.imageUrl = s3Uploader.upload(placeUpdateDTO.getImage(), "place");
        this.subImageUrl = s3Uploader.upload(placeUpdateDTO.getSubImage(), "place/sub");
        this.filterImageUrl = s3Uploader.upload(placeUpdateDTO.getFilterImage(), "place/filter");
    }

    public void changePlaceInfo(List<PlaceInfo> placeInfos) {
        this.placeInfos = placeInfos;
    }

    public void findTagSpeech(String speech) {
        this.tags = Optional.ofNullable(this.tags).orElse(new ArrayList<>()).stream()
                .filter(tag -> tag.getSpeech().equals(speech))
                .collect(Collectors.toList());
    }

    public void findInfoSpeech(String speech) {
        this.placeInfos = Optional.ofNullable(this.placeInfos).orElse(new ArrayList<>()).stream()
                .filter(info -> info.getSpeech().equals(speech))
                .collect(Collectors.toList());
    }
}
