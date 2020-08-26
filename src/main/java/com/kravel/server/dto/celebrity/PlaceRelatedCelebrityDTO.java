package com.kravel.server.api.dto.celebrity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@Alias("celebrityArticleDTO")
public class PlaceRelatedCelebrityDTO {
    private long placeId;
    private String subject;
    private String imageUrl;
    private String mediaName;
    private List<String> celebrities;
}
