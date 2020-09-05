package com.kravel.server.dto.media;

import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class MediaDTO {
    private long mediaId;
    private String title;
    private String imageUrl;
    private String contents;
    private String year;

    public static MediaDTO fromEntity(Media entity) {
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setMediaId(entity.getId());
        mediaDTO.setTitle(entity.getMediaInfos().stream()
                .findFirst()
                .orElse(new MediaInfo()).getTitle());
        mediaDTO.setImageUrl(entity.getImageUrl());
        mediaDTO.setContents(entity.getMediaInfos().stream()
                .findFirst()
                .orElse(new MediaInfo()).getContents());
        mediaDTO.setYear(entity.getYear().toString());

        return mediaDTO;
    }
}
