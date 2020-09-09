package com.kravel.server.dto.celebrity;

import com.kravel.server.dto.place.PlaceDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CelebrityDetailDTO {
    private CelebrityDTO celebrity;
    private List<PlaceDTO> places;

    @Builder
    public CelebrityDetailDTO(CelebrityDTO celebrity, List<PlaceDTO> places) {
        this.celebrity = celebrity;
        this.places = places;
    }
}
