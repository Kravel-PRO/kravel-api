package com.kravel.server.api.article.service;

import com.kravel.server.api.article.Model.Article;
import com.kravel.server.api.article.dto.*;
import com.kravel.server.api.article.mapper.ArticleMapper;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> findAllPlaces(Map<String,Object> param) throws Exception {

        List<Article> articleList = articleMapper.findAllPlaces(param);
        if (articleList.isEmpty()) {
            throw new NotFoundException("IS not exist Article List");
        }

        return articleList;
    }

    public ArticleDetailDTO findPlaceById(Map<String, Object> param) throws Exception {

        ArticleDetailDTO articleDetailDTO = articleMapper.findPlaceById(param);
        if (articleDetailDTO.getSubject().isEmpty()) {
            throw new NotFoundException("Is not exist Article");
        }

        List<CelebrityDTO> celebrityDTOList = articleMapper.findAllCelebrities(param);
        articleDetailDTO.setCelebrityList(celebrityDTOList);

        param.put("sort", "CREATE_DE");
        param.put("order", "DESC");
        param.put("offset", 0);
        param.put("max", 6);
        List<ArticleReviewListDTO> articleReviewListDTOList = articleMapper.findAllReviews(param);
        articleDetailDTO.setReviewList(articleReviewListDTOList);

        return articleDetailDTO;
    }

    public boolean handleArticleScrap(Map<String, Object> param) throws Exception {
        int savedScrap = articleMapper.checkExistArticleScrap(param);

        if((boolean) param.get("scrapState") && savedScrap == 0) {
            return articleMapper.saveArticleScrap(param) != 0;

        } else if (savedScrap >= 1) {
            return articleMapper.removeArticleScrap(param) != 0;

        } else {
            throw new InvalidRequestException("It is not valid scarp state");
        }
    }

}
