package com.kravel.server.dto.update;

import com.kravel.server.model.place.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TagUpdateDTO {

    private String name;
    private String speech;

    public static TagUpdateDTO fromEntity(Tag entity) {
        TagUpdateDTO tagUpdateDTO = new TagUpdateDTO();
        tagUpdateDTO.setName(entity.getName());

        return tagUpdateDTO;
    }
}
