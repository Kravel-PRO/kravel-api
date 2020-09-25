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
    public Place(long id, double latitude, double longitude, String imageUrl, String subImageUrl, String filterImageUrl) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.subImageUrl = subImageUrl;
        this.filterImageUrl = filterImageUrl;
    }

    public void fromPlaceUpdateDTO(PlaceUpdateDTO placeUpdateDTO, S3Uploader s3Uploader, ObjectMapper objectMapper, PlaceCelebrityRepository placeCelebrityRepository, MediaRepository mediaRepository) throws Exception {
        this.bus = placeUpdateDTO.getBus();
        this.subway = placeUpdateDTO.getSubway();
        this.latitude = placeUpdateDTO.getLatitude();
        this.longitude = placeUpdateDTO.getLongitude();

        KorPlaceInfoUpdateDTO korPlaceInfoUpdateDTO = objectMapper.readValue(placeUpdateDTO.getKorInfo(), KorPlaceInfoUpdateDTO.class);
        EngPlaceInfoUpdateDTO engPlaceInfoUpdateDTO = objectMapper.readValue(placeUpdateDTO.getEngInfo(), EngPlaceInfoUpdateDTO.class);
        List<Integer> celebrities = objectMapper.readValue(placeUpdateDTO.getCelebrities(), new TypeReference<List<Integer >>(){});

        this.placeInfos.add(new PlaceInfo(this, korPlaceInfoUpdateDTO));
        this.placeInfos.add(new PlaceInfo(this, engPlaceInfoUpdateDTO));
        this.tags.add(new Tag(this, korPlaceInfoUpdateDTO));
        this.tags.add(new Tag(this, engPlaceInfoUpdateDTO));

        if (celebrities.size() != 0) {
            if (this.placeCelebrities.size() != 0) {
                this.placeCelebrities.forEach(placeCelebrityRepository::delete);
                this.placeCelebrities = new ArrayList<>();
            }
            celebrities.forEach(celebrity -> {
                PlaceCelebrity placeCelebrity = new PlaceCelebrity(this, celebrity);
                this.placeCelebrities.add(placeCelebrity);
            });
        }
        if (placeUpdateDTO.getMedia() != 0) {
            if (this.media != null) {
                mediaRepository.delete(this.media);
            }
            this.media = new Media(placeUpdateDTO.getMedia());
        }

        if (placeUpdateDTO.getImage() != null) {
            if (this.imageUrl != null) {
                s3Uploader.removeS3Object(this.imageUrl);
            }
            this.imageUrl = s3Uploader.upload(placeUpdateDTO.getImage(), "place/represent");
        }
        if (placeUpdateDTO.getSubImage() != null) {
            if (this.subImageUrl != null) {
                s3Uploader.removeS3Object(this.subImageUrl);
            }
            this.subImageUrl = s3Uploader.upload(placeUpdateDTO.getSubImage(), "place/sub");
        }
        if (placeUpdateDTO.getFilterImage() != null) {
            if (this.filterImageUrl != null) {
                s3Uploader.removeS3Object(this.filterImageUrl);
            }
            this.filterImageUrl = s3Uploader.upload(placeUpdateDTO.getFilterImage(), "place/filter");
        }
    }

    public void changePlaceInfo(List<PlaceInfo> placeInfos) {
        this.placeInfos = placeInfos;
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
