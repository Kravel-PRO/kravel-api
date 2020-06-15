package com.kravel.server.api.article.dto.celebrity;

import com.kravel.server.api.article.model.Celebrity;
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
public class CelebrityArticleDTO {
    private long articleId;
    private String subject;
    private String imgUrl;
    private String mediaName;
    private List<String> celebrities;

    public
    CelebrityArticleDTO(CelebrityDTO celebrityDTO, ExCelebrityArticleDTO exCelebrityArticleDTO) {
        this.articleId = exCelebrityArticleDTO.getArticleId();
        this.subject = exCelebrityArticleDTO.getSubject();
        this.imgUrl = exCelebrityArticleDTO.getImgUrl();
        this.mediaName = exCelebrityArticleDTO.getMediaName();
        this.celebrities = new ArrayList<>(Arrays.asList(exCelebrityArticleDTO.getCelebrities().split(",")));

        this.celebrities.remove(celebrityDTO.getCelebrityName());
    }
}
