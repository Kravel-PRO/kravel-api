package com.kravel.server.dto.media;

import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaInfo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MediaOverviewDTO {

    private long mediaId;
    private String title;
    private String imageUrl;
    private String year;

    public static MediaOverviewDTO fromEntity(Media entity) {
        MediaOverviewDTO mediaOverviewDTO = new MediaOverviewDTO();
        mediaOverviewDTO.setMediaId(entity.getId());
        mediaOverviewDTO.setTitle(entity.getMediaInfos().stream()
                .findFirst()
                .orElse(new MediaInfo()).getTitle());
        mediaOverviewDTO.setYear(entity.getYear());
        mediaOverviewDTO.setImageUrl(entity.getImageUrl());

        return mediaOverviewDTO;
    }
}
