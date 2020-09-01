package com.kravel.server.dto.media;

import com.kravel.server.model.media.Media;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

@Getter @Setter
public class MediaOverviewDTO {

    private long mediaId;
    private String name;
    private String imageUrl;
    private int year;

    public static MediaOverviewDTO fromEntity(Media entity) {
        MediaOverviewDTO mediaOverviewDTO = new MediaOverviewDTO();
        mediaOverviewDTO.setMediaId(entity.getId());
        mediaOverviewDTO.setName(entity.getName());
        mediaOverviewDTO.setYear(entity.getYear().getYear());
        mediaOverviewDTO.setImageUrl(entity.getImageUrl());

        return mediaOverviewDTO;
    }
}
