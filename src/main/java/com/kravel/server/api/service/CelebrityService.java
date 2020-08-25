package com.kravel.server.api.service;

import com.kravel.server.api.dto.celebrity.PlaceRelatedCelebrityDTO;
import com.kravel.server.api.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.api.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.dto.celebrity.RawPlaceRelatedCelebrityDTO;
import com.kravel.server.api.mapper.CelebrityMapper;
import com.kravel.server.api.mapper.PlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CelebrityService {

    private final CelebrityMapper celebrityMapper;
    private final PlaceMapper placeMapper;

    public List<CelebrityDTO> findAllCelebrities(Map<String, Object> param) throws Exception {
        return celebrityMapper.findAllCelebrities(param);
    }

    public CelebrityDetailDTO findCelebrityById(Map<String, Object> param) throws Exception {

        CelebrityDetailDTO celebrityDetailDTO = new CelebrityDetailDTO();

        CelebrityDTO celebrityDTO = celebrityMapper.findCelebrityById(param);
        celebrityDetailDTO.setCelebrity(celebrityDTO);

        RawPlaceRelatedCelebrityDTO rawPlaceRelatedCelebrityDTO = placeMapper.findAllPlaceByCelebrity(param);
        PlaceRelatedCelebrityDTO placeRelatedCelebrityDTO = new PlaceRelatedCelebrityDTO(celebrityDTO, rawPlaceRelatedCelebrityDTO);

        celebrityDetailDTO.setPlaces(placeRelatedCelebrityDTO);
        return celebrityDetailDTO;
    }
}
