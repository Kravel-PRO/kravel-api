package com.kravel.server.dto.article;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("articleMapDTO")
public class PlaceMapDTO {
    private long articleId;
    private String title;
    private String imageUrl;

    private long mediaId;
    private String mediaName;

    private List<CelebrityDTO> celebrities;
}
