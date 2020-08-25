package com.kravel.server.api.dto.article;

import com.kravel.server.api.dto.celebrity.CelebrityDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("articleMapDTO")
public class ArticleMapDTO {
    private long articleId;
    private String subject;
    private String imgUrl;

    private long mediaId;
    private String mediaName;

    private List<CelebrityDTO> celebrities;
}
