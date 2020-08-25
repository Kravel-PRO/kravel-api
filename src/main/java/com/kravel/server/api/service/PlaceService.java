package com.kravel.server.api.service;

import com.kravel.server.api.dto.article.ArticleDetailDTO;
import com.kravel.server.api.dto.article.ArticleMapDTO;
import com.kravel.server.api.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.mapper.PlaceMapper;
import com.kravel.server.api.mapper.ReviewMapper;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceMapper placeMapper;
    private final ReviewMapper reviewMapper;

    public List<ArticleMapDTO> findAllPlaces(Map<String,Object> param) throws Exception {

        List<ArticleMapDTO> articleMapDTOList = placeMapper.findAllPlaces(param);
        if (articleMapDTOList.isEmpty()) {
            throw new NotFoundException("IS not exist Article List");
        }

        for (int i=0; i<articleMapDTOList.size(); i++) {
            param.put("articleId", articleMapDTOList.get(i).getArticleId());
            articleMapDTOList.get(i).setCelebrities(placeMapper.findAllCelebrities(param));
        }

        return articleMapDTOList;
    }

    public ArticleDetailDTO findPlaceById(Map<String, Object> param) throws Exception {

        ArticleDetailDTO articleDetailDTO = placeMapper.findPlaceById(param);
        if (articleDetailDTO.getSubject().isEmpty()) {
            throw new NotFoundException("Is not exist Article");
        }

        List<CelebrityDTO> celebrityDTOs = placeMapper.findAllCelebrities(param);
        articleDetailDTO.setCelebrities(celebrityDTOs);

        articleDetailDTO.setImgs(placeMapper.findAllArticleImg(param));

        return articleDetailDTO;
    }

    public boolean handleArticleScrap(Map<String, Object> param) throws Exception {
        int savedScrap = placeMapper.checkExistArticleScrap(param);

        if((boolean) param.get("scrapState") && savedScrap == 0) {
            return placeMapper.saveArticleScrap(param) != 0;

        } else if (savedScrap >= 1) {
            return placeMapper.removeArticleScrap(param) != 0;

        } else {
            throw new InvalidRequestException("It is not valid scarp state");
        }
    }
}