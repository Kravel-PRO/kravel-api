package com.kravel.server.dto;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.dto.media.MediaDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.mapping.CelebrityMedia;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
public class SearchDTO {

    private List<CelebrityDTO> celebrities = new ArrayList<>();
    private List<MediaOverviewDTO> medias = new ArrayList<>();

    @Builder
    public SearchDTO(List<CelebrityDTO> celebrities, List<MediaOverviewDTO> medias) {
        this.celebrities = celebrities;
        this.medias = medias;
    }
}
