package com.kravel.server.service;

import com.kravel.server.dto.celebrity.PlaceRelatedCelebrityDTO;
import com.kravel.server.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.mapper.CelebrityMapper;
import com.kravel.server.mapper.PlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CelebrityService {

    private final CelebrityMapper celebrityMapper;
    private final PlaceMapper placeMapper;

    public List<CelebrityDTO> findAllCelebrities(String search, Pageable pageable) throws Exception {
        List<Celebrity> celebrities = celebrityMapper.findAllCelebrities(param);
        return celebrities.stream().map(CelebrityDTO::fromEntity).collect(Collectors.toList());
    }

    public CelebrityDetailDTO findCelebrityById(Map<String, Object> param) throws Exception {

        CelebrityDetailDTO celebrityDetailDTO = new CelebrityDetailDTO();

        Celebrity celebrity = celebrityMapper.findCelebrityById(param);
        CelebrityDTO celebrityDTO = CelebrityDTO.fromEntity(celebrity);
        celebrityDetailDTO.setCelebrity(celebrityDTO);

        List<PlaceRelatedCelebrityDTO> placeRelatedCelebrityDTOs = placeMapper.findAllPlaceAndPlaceInfoByCelebrity(param);
        for (PlaceRelatedCelebrityDTO placeRelatedCelebrityDTO : placeRelatedCelebrityDTOs) {
            placeRelatedCelebrityDTO.setCelebrities(celebrityMapper
                    .findAllCelebrityByPlace(placeRelatedCelebrityDTO.getPlaceId()).stream()
                    .map(Celebrity::getCelebrityName)
                    .collect(Collectors.toList()));
        }

        celebrityDetailDTO.setPlaces(placeRelatedCelebrityDTOs);
        return celebrityDetailDTO;
    }
}
