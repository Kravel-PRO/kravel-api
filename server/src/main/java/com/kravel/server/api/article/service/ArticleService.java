package com.kravel.server.api.article.service;

import com.kravel.server.api.article.Model.Article;
import com.kravel.server.api.article.dto.ArticleDetailDTO;
import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.dto.ArticleReviewListDTO;
import com.kravel.server.api.article.dto.CelebrityDTO;
import com.kravel.server.api.article.mapper.ArticleMapper;
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

    public ArticleDetailDTO findPlaceByArticleId(Map<String, Object> param) throws Exception {

        ArticleDetailDTO articleDetailDTO = articleMapper.findPlaceByArticleId(param);
        if (articleDetailDTO.getSubject().isEmpty()) {
            throw new NotFoundException("Is not exist Article");
        }

        List<CelebrityDTO> celebrityDTOList = articleMapper.findCelebrityListByArticleId(param);
        articleDetailDTO.setCelebrityList(celebrityDTOList);

        param.put("max", 6);
        List<ArticleReviewListDTO> articleReviewListDTOList = articleMapper.findReviewListByArticleId(param);
        articleDetailDTO.setReviewList(articleReviewListDTOList);

        return articleDetailDTO;
    }

    /**
     * 리뷰 리스트 버튼 눌렀을 때
     * @param param
     * @return
     * @throws Exception
     */
    public List<ArticleReviewListDTO> findReviewListByArticleId(Map<String, Object> param) throws Exception {

        List<ArticleReviewListDTO> articleReviewListDTOList = articleMapper.findReviewListByArticleId(param);
        if (articleReviewListDTOList.isEmpty()) {
            throw new NotFoundException("Is not exist review");
        }
        return articleReviewListDTOList;
    }

    public ArticleReviewDTO findReviewDetailByReviewId(Map<String, Object> param) throws Exception {

        ArticleReviewDTO articleReviewDTO = articleMapper.findReviewLikeCntByReviewId(param);
        articleReviewDTO.setRwImg(articleMapper.findReviewDetailImgByReviewId(param));

        if (articleReviewDTO.getRwImg().isEmpty()) {
            throw new NotFoundException("Is ot exist review");
        }

        return articleReviewDTO;
    }

}
