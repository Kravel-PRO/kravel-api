package com.kravel.server.dto.celebrity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

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
