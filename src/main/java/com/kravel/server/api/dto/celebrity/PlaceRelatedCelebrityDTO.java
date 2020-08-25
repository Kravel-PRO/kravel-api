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
    private long articleId;
    private String subject;
    private String imageUrl;
    private String mediaName;
    private List<String> celebrities;

    public PlaceRelatedCelebrityDTO(CelebrityDTO celebrityDTO, RawPlaceRelatedCelebrityDTO rawPlaceRelatedCelebrityDTO) {
        this.articleId = rawPlaceRelatedCelebrityDTO.getArticleId();
        this.subject = rawPlaceRelatedCelebrityDTO.getSubject();
        this.imageUrl = rawPlaceRelatedCelebrityDTO.getImageUrl();
        this.mediaName = rawPlaceRelatedCelebrityDTO.getMediaName();
        this.celebrities = new ArrayList<>(Arrays.asList(rawPlaceRelatedCelebrityDTO.getCelebrities().split(",")));

        this.celebrities.remove(celebrityDTO.getCelebrityName());
    }
}
