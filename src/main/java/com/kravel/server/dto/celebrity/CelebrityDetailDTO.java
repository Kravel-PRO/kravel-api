package com.kravel.server.dto.celebrity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CelebrityDetailDTO {
    private CelebrityDTO celebrity;
    private List<PlaceRelatedCelebrityDTO> places;
}
