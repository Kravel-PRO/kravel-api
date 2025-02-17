package com.kravel.server.dto.celebrity;

import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.celebrity.CelebrityInfo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CelebrityDTO {
    private long celebrityId;
    private String celebrityName;
    private String imageUrl;

    public static CelebrityDTO fromEntity(Celebrity entity) {
        CelebrityDTO celebrityDTO = new CelebrityDTO();
        celebrityDTO.setCelebrityId(entity.getId());
        celebrityDTO.setCelebrityName(entity.getCelebrityInfos().stream()
                .findFirst()
                .orElse(new CelebrityInfo()).getName());
        celebrityDTO.setImageUrl(entity.getImageUrl());

        return celebrityDTO;
    }
}
