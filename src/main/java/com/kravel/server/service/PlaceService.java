//package com.kravel.server.service;
//
//import com.kravel.server.dto.article.PlaceDTO;
//import com.kravel.server.dto.article.PlaceMapDTO;
//import com.kravel.server.dto.celebrity.CelebrityDTO;
//import com.kravel.server.mapper.PlaceMapper;
//import com.kravel.server.mapper.ReviewMapper;
//import com.kravel.server.common.util.exception.InvalidRequestException;
//import com.kravel.server.common.util.exception.NotFoundException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class PlaceService {
//
//    private final PlaceMapper placeMapper;
//    private final ReviewMapper reviewMapper;
//
//    public List<PlaceMapDTO> findAllPlaces(Map<String,Object> param) throws Exception {
//
//        List<PlaceMapDTO> placeMapDTOs = placeMapper.findAllPlaces(param);
//        if (placeMapDTOs.isEmpty()) {
//            throw new NotFoundException("🔥 error: is not exist Article List");
//        }
//
//        for (int i = 0; i< placeMapDTOs.size(); i++) {
//            param.put("articleId", placeMapDTOs.get(i).getArticleId());
//            placeMapDTOs.get(i)
//                    .setCelebrities(placeMapper
//                            .findAllCelebritiesByPlace(param).stream()
//                            .map(CelebrityDTO::fromEntity)
//                            .collect(Collectors.toList()));
//        }
//
//        return placeMapDTOs;
//    }
//
//    public PlaceDTO findPlaceById(Map<String, Object> param) throws Exception {
//
//        PlaceDTO placeDTO = placeMapper.findPlaceById(param);
//        if (placeDTO.getTitle().isEmpty()) {
//            throw new NotFoundException("Is not exist Article");
//        }
//
//        placeDTO.setCelebrities(placeMapper
//                .findAllCelebritiesByPlace(param).stream()
//                .map(CelebrityDTO::fromEntity)
//                .collect(Collectors.toList()));
//
//        return placeDTO;
//    }
//
//    public boolean handleArticleScrap(Map<String, Object> param) throws Exception {
//        int savedScrap = placeMapper.checkExistArticleScrap(param);
//
//        if((boolean) param.get("scrapState") && savedScrap == 0) {
//            return placeMapper.savePlaceScrap(param) != 0;
//
//        } else if (savedScrap >= 1) {
//            return placeMapper.removePlaceScrap(param) != 0;
//
//        } else {
//            throw new InvalidRequestException("It is not valid scarp state");
//        }
//    }
//}