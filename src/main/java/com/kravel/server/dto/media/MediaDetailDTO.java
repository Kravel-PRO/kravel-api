package com.kravel.server.dto.media;

import com.kravel.server.dto.place.PlaceDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MediaDetailDTO {
    private MediaDTO media;
    private List<PlaceDTO> places;

    @Builder
    public MediaDetailDTO(MediaDTO media, List<PlaceDTO> places) {
        this.media = media;
        this.places = places;
    }
}
