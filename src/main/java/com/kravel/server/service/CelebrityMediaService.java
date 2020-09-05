package com.kravel.server.service;

import com.kravel.server.dto.SearchDTO;
import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.dto.media.MediaOverviewDTO;
import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.mapping.CelebrityMediaQueryRepository;
import com.kravel.server.model.media.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CelebrityMediaService {

    private final CelebrityMediaQueryRepository celebrityMediaQueryRepository;

    public SearchDTO findBySearch(String search, String speech, Pageable pageable) throws Exception {
        List<Celebrity> searchedCelebrities = celebrityMediaQueryRepository.findCelebrityBySearch(search, speech, pageable);
        List<Media> searchedMedias = celebrityMediaQueryRepository.findMediaBySearch(search, speech, pageable);

        return SearchDTO.builder()
                .celebrities(searchedCelebrities.stream().map(CelebrityDTO::fromEntity).collect(Collectors.toList()))
                .medias(searchedMedias.stream().map(MediaOverviewDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
