package com.kravel.server.api.dto.media;

import com.kravel.server.api.model.Media;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@Alias("mediaOverviewDTO")
public class MediaOverviewDTO {

    private long mediaId;
    private String name;
    private String openYear;

    public static MediaOverviewDTO fromEntity(Media entity) {
        MediaOverviewDTO mediaOverviewDTO = new MediaOverviewDTO();
        mediaOverviewDTO.setMediaId(entity.getMediaId());
        mediaOverviewDTO.setName(entity.getName());
        mediaOverviewDTO.setOpenYear(entity.getOpenYear());

        return mediaOverviewDTO;
    }
}
