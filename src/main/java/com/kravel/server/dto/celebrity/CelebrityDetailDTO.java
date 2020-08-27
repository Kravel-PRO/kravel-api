package com.kravel.server.dto.celebrity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CelebrityDetailDTO {
    private CelebrityDTO celebrity;
    private List<PlaceRelatedCelebrityDTO> places;

    @Builder
    public CelebrityDetailDTO(CelebrityDTO celebrity, List<PlaceRelatedCelebrityDTO> places) {
        this.celebrity = celebrity;
        this.places = places;
    }
}
