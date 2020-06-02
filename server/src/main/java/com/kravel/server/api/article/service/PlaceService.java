package com.kravel.server.api.article.service;

import com.kravel.server.api.article.Model.Article;
import com.kravel.server.api.article.Model.ArticleReview;
import com.kravel.server.api.article.dto.ArticleDetailDTO;
import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.dto.CelebrityDTO;
import com.kravel.server.api.article.mapper.PlaceMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PlaceService {

    @Autowired
    private PlaceMapper placeMapper;

    public List<Article> findAllPlaces(Map<String,Object> param) throws Exception {

        List<Article> articleList = placeMapper.findAllPlaces(param);
        if (articleList.isEmpty()) {
            throw new NotFoundException("IS not exist Article List");
        }

        return articleList;
    }

    public ArticleDetailDTO findPlaceByArticleId(Map<String, Object> param) throws Exception {

        ArticleDetailDTO articleDetailDTO = placeMapper.findPlaceByArticleId(param);
        if (articleDetailDTO.getSubject().isEmpty()) {
            throw new NotFoundException("IS not exist Article");
        }

        List<CelebrityDTO> celebrityDTOList = placeMapper.findCelebrityListByArticleId(param);

        param.put("max", 6);
        List<ArticleReviewDTO> articleReviewDTOList = placeMapper.findReviewListByArticleId(param);

        articleDetailDTO.setCelebrityList(celebrityDTOList);
        articleDetailDTO.setReviewList(articleReviewDTOList);

        return articleDetailDTO;
    }

}
