package com.kravel.server.api.dto.media;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class PlaceRelatedMediaDTO {
    private long articleId;
    private String subject;
    private String imgUrl;
    private List<String> celebrities;

    public PlaceRelatedMediaDTO(RawPlaceRelatedMediaDTO exMediaArticleDTO) {
        this.articleId = exMediaArticleDTO.getArticleId();
        this.subject = exMediaArticleDTO.getSubject();
        this.imgUrl = exMediaArticleDTO.getImgUrl();
        this.celebrities = new ArrayList<>(Arrays.asList(exMediaArticleDTO.getCelebrities().split(",")));
    }
}
