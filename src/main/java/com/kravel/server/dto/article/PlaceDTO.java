package com.kravel.server.dto.article;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.model.place.Place;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@ToString
@Alias("placeDTO")
public class PlaceDTO {
    private long placeId;

    private String title;
    private String contents;
    private String imageUrl;

    private String location;
    private String latitude;
    private String longitude;

    private double grade;
    private double weight;

    private long mediaId;
    private String mediaName;

    private int reviewCnt;
    private List<CelebrityDTO> celebrities;

    public static PlaceDTO fromEntity(Place entity) {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setPlaceId(entity.getId());
        placeDTO.setTitle(entity.getTitle());
        placeDTO.setContents(entity.getPlaceInfos().stream().filter(placeInfo -> placeInfo.getSpeech() == "KOA").findFirst().get().getContents());
        placeDTO.setImageUrl(entity.getImageUrl());
        placeDTO.setLocation(entity.getLocation());
        placeDTO.setLatitude(entity.getLatitude());
        placeDTO.setLongitude(entity.getLongitude());
        placeDTO.setGrade(entity.getGrade());
        placeDTO.setWeight(entity.getWeight());
        placeDTO.setMediaId(entity.getMedia().getId());
        placeDTO.setMediaName(entity.getMedia().getName());
        placeDTO.setReviewCnt(entity.getReviews().size());
        placeDTO.setCelebrities(entity.getPlaceCelebrities().stream()
                .map(placeCelebrity -> CelebrityDTO.fromEntity(placeCelebrity.getCelebrity()))
                .collect(Collectors.toList()));

        return placeDTO;
    }
}
