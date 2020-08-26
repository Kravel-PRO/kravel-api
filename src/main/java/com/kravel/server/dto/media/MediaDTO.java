package com.kravel.server.dto.media;

import com.kravel.server.model.media.Media;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

@Getter @Setter
@Alias("mediaDTO")
public class MediaDTO {
    private long mediaId;
    private String name;
    private String imageUrl;
    private String contents;
    private LocalDate year;

    public static MediaDTO fromEntity(Media entity) {
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setMediaId(entity.getId());
        mediaDTO.setName(entity.getName());
        mediaDTO.setImageUrl(entity.getImageUrl());
        mediaDTO.setContents(entity.getContents());
        mediaDTO.setYear(entity.getYear());

        return mediaDTO;
    }
}
