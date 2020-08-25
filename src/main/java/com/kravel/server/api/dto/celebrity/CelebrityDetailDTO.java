package com.kravel.server.api.dto.celebrity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CelebrityDetailDTO {
    private CelebrityDTO celebrity;
    private PlaceRelatedCelebrityDTO places;
}
