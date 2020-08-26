package com.kravel.server.api.dto.media;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class PlaceRelatedMediaDTO {
    private long placeId;
    private String title;
    private String imageUrl;
    private List<String> celebrities = new ArrayList<>();
}
