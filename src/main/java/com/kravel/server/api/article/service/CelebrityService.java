package com.kravel.server.api.article.service;

import com.kravel.server.api.article.dto.celebrity.CelebrityArticleDTO;
import com.kravel.server.api.article.dto.celebrity.ExCelebrityArticleDTO;
import com.kravel.server.api.article.dto.review.ArticleReviewListDTO;
import com.kravel.server.api.article.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.article.mapper.CelebrityMapper;
import com.kravel.server.api.article.mapper.ReviewMapper;
import com.kravel.server.api.article.model.Celebrity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CelebrityService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private CelebrityMapper celebrityMapper;

    public List<CelebrityDTO> findAllCelebrities(Map<String, Object> param) throws Exception {
        return celebrityMapper.findAllCelebrities(param);
    }

    public Map<String, Object> findCelebrityById(Map<String, Object> param) throws Exception {
        Map<String, Object> result = new HashMap<>();

        CelebrityDTO celebrityDTO = celebrityMapper.findCelebrityById(param);
        result.put("celebrity", celebrityDTO);

        List<ArticleReviewListDTO> articleReviewListDTOList = reviewMapper.findAllReviews(param);
        result.put("reviewList", articleReviewListDTOList);

        ExCelebrityArticleDTO exCelebrityArticleDTO = celebrityMapper.findAllArticleByCelebrity(param);
        CelebrityArticleDTO celebrityArticleDTO = new CelebrityArticleDTO(celebrityDTO, exCelebrityArticleDTO);

        result.put("articleList", celebrityArticleDTO);

        return result;
    }
}
