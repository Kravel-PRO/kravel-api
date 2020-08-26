package com.kravel.server.api.service;

import com.kravel.server.api.dto.celebrity.PlaceRelatedCelebrityDTO;
import com.kravel.server.api.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.api.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.mapper.CelebrityMapper;
import com.kravel.server.api.mapper.PlaceMapper;
import com.kravel.server.api.model.Celebrity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CelebrityService {

    private final CelebrityMapper celebrityMapper;
    private final PlaceMapper placeMapper;

    public List<CelebrityDTO> findAllCelebrities(Map<String, Object> param) throws Exception {
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
