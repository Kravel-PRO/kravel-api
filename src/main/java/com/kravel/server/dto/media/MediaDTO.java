package com.kravel.server.dto.media;

import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.place.PlaceDetailDTO;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
public class MediaDTO {
    private long mediaId;
    private String title;
    private String imageUrl;
    private String contents;
    private List<PlaceDTO> places = new ArrayList<>();
    private String year;

    public static MediaDTO fromEntity(Media entity) {
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setMediaId(entity.getId());
        mediaDTO.setTitle(entity.getMediaInfos().stream()
                .findFirst()
                .orElse(new MediaInfo()).getTitle());
        mediaDTO.setImageUrl(entity.getImageUrl());
        mediaDTO.setContents(entity.getMediaInfos().stream()
                .findFirst()
                .orElse(new MediaInfo()).getContents());
        mediaDTO.setYear(entity.getYear().toString());
        mediaDTO.setPlaces(Optional.ofNullable(entity.getPlaces()).orElse(new ArrayList<>()).stream().map(PlaceDTO::fromEntity).collect(Collectors.toList()));

        return mediaDTO;
    }
}
