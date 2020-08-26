package com.kravel.server.api.dto.media;

import com.kravel.server.api.model.Media;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@Alias("mediaDTO")
public class MediaDTO {
    private long mediaId;
    private String name;
    private String imageUrl;
    private String contents;
    private String openYear;

    public static MediaDTO fromEntity(Media entity) {
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setMediaId(entity.getMediaId());
        mediaDTO.setName(entity.getName());
        mediaDTO.setImageUrl(entity.getImageUrl());
        mediaDTO.setContents(entity.getContents());
        mediaDTO.setOpenYear(entity.getOpenYear());

        return mediaDTO;
    }
}
