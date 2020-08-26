package com.kravel.server.api.dto.celebrity;

import com.kravel.server.api.model.Celebrity;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("celebrityDTO")
public class CelebrityDTO {
    private long celebrityId;
    private String name;
    private String imageUrl;

    public static CelebrityDTO fromEntity(Celebrity entity) {
        CelebrityDTO celebrityDTO = new CelebrityDTO();
        celebrityDTO.setCelebrityId(entity.getCelebrityId());
        celebrityDTO.setName(entity.getName());
        celebrityDTO.setImageUrl(entity.getImageUrl());

        return celebrityDTO;
    }
}
