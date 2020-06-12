package com.kravel.server.api.article.service;

import com.kravel.server.api.article.Model.Celebrity;
import com.kravel.server.api.article.dto.ArticleReviewListDTO;
import com.kravel.server.api.article.dto.CelebrityDTO;
import com.kravel.server.api.article.dto.ReviewDTO;
import com.kravel.server.api.article.mapper.CelebrityMapper;
import com.kravel.server.api.article.mapper.ReviewMapper;
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

        List<ArticleReviewListDTO> articleReviewListDTOList = reviewMapper.findAllReviews(param);
        result.put("reviewList", articleReviewListDTOList);

        // TODO 다른 value 파편도 모아야함.
        return result;
    }
}
