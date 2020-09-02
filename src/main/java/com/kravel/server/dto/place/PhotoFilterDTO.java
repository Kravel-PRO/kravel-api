package com.kravel.server.dto.place;

import com.kravel.server.model.place.PhotoFilter;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PhotoFilterDTO {
    private String guideLine;
    private String subImageUrl;

    public static PhotoFilterDTO fromEntity(PhotoFilter entity) {
        PhotoFilterDTO photoFilterDTO = new PhotoFilterDTO();
        photoFilterDTO.setGuideLine(entity.getGuideLine());
        photoFilterDTO.setSubImageUrl(entity.getPlace().getSubImageUrl());

        return photoFilterDTO;
    }
}
