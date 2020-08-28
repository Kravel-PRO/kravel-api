package com.kravel.server.service;

import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.celebrity.PlaceRelatedCelebrityDTO;
import com.kravel.server.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.model.celebrity.Celebrity;
import com.kravel.server.model.celebrity.CelebrityQueryFactory;
import com.kravel.server.model.celebrity.CelebrityRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CelebrityService {

    private final CelebrityQueryFactory celebrityQueryFactory;
    private final CelebrityRepository celebrityRepository;

    private final PlaceQueryRepository placeQueryRepository;

    public List<CelebrityDTO> findAllCelebrities(String search, Pageable pageable) throws Exception {
        Page<Celebrity> celebrities = celebrityQueryFactory.finaAllCelebrity(search, pageable);
        return celebrities.stream().map(CelebrityDTO::fromEntity).collect(Collectors.toList());
    }

    public CelebrityDetailDTO findCelebrityById(long celebrityId, String speech) throws Exception {


        Celebrity celebrity = celebrityRepository.findById(celebrityId).orElseThrow(() -> new NotFoundException("🔥 error: celebrity is not exist"));


        CelebrityDTO celebrityDTO = CelebrityDTO.fromEntity(celebrity);

        List<Place> places = placeQueryRepository.findAllByCelebrity(celebrityId, speech);
        List<PlaceRelatedCelebrityDTO> placeRelatedCelebrityDTOs = places.stream().map(PlaceRelatedCelebrityDTO::fromEntity).collect(Collectors.toList());;

        CelebrityDetailDTO celebrityDetailDTO = CelebrityDetailDTO.builder()
                .celebrity(celebrityDTO)
                .places(placeRelatedCelebrityDTOs)
                .build();

        return celebrityDetailDTO;
    }
}
