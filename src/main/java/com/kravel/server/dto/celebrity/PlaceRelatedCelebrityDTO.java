package com.kravel.server.dto.celebrity;

import com.kravel.server.model.place.Place;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Alias("celebrityArticleDTO")
public class PlaceRelatedCelebrityDTO {
    private long placeId;
    private String title;
    private String imageUrl;
    private String mediaName;
    private List<String> celebrities;

    public static PlaceRelatedCelebrityDTO fromEntity(Place entity) {
        PlaceRelatedCelebrityDTO placeRelatedCelebrityDTO = new PlaceRelatedCelebrityDTO();
        placeRelatedCelebrityDTO.setPlaceId(entity.getId());
        placeRelatedCelebrityDTO.setTitle(entity.getPlaceInfos().stream().filter(info -> info.getSpeech() == "KOR").findFirst().get().getTitle());
        placeRelatedCelebrityDTO.setImageUrl(entity.getImageUrl());
        placeRelatedCelebrityDTO.setMediaName(entity.getMedia().getName());
        placeRelatedCelebrityDTO.setCelebrities(entity.getPlaceCelebrities().stream().map(placeCelebrity -> placeCelebrity.getCelebrity().getName()).collect(Collectors.toList()));

        return placeRelatedCelebrityDTO;
    }
}
