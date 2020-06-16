package com.kravel.server.api.article.dto.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Setter
@Getter
@Alias("ImgDTO")
public class ImgDTO {
    private long imgId;
    private String imgUrl;
    private boolean represent;
}
