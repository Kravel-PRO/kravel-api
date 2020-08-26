package com.kravel.server.dto.article;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter @Setter
@ToString
@Alias("placeDTO")
public class PlaceDTO {
    private long articleId;

    private String title;
    private String contents;
    private String imageUrl;

    private String location;
    private String latitude;
    private String longitude;

    private double grade;
    private double weight;

    private long mediaId;
    private String mediaName;

    private int reviewCnt;
    private List<CelebrityDTO> celebrities;
}
