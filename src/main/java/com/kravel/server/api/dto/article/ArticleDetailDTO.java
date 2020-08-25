package com.kravel.server.api.dto.article;

import com.kravel.server.api.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.dto.review.ImgDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("articleDetailDTO")
public class ArticleDetailDTO {
    private long articleId;

    private String subject;
    private String contents;

    private String location;
    private String latitude;
    private String longitude;

    private double grade;
    private double weight;

    private long mediaId;
    private String mediaName;

    private int reviewCnt;
    private List<CelebrityDTO> celebrities;
    private List<ImgDTO> imgs;
}
