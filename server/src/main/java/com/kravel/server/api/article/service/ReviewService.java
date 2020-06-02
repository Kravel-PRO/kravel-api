package com.kravel.server.api.article.service;

import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.dto.ArticleReviewListDTO;
import com.kravel.server.api.article.mapper.ArticleMapper;
import com.kravel.server.api.article.mapper.ReviewMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    // 리뷰 리스트 눌렀을 때
    public List<ArticleReviewListDTO> findAllReviews(Map<String, Object> param) throws Exception {

        List<ArticleReviewListDTO> articleReviewListDTOList = articleMapper.findAllReviews(param);
        if (articleReviewListDTOList.isEmpty()) {
            throw new NotFoundException("Is not exist review");
        }
        return articleReviewListDTOList;
    }

    public ArticleReviewDTO findReviewDetailById(Map<String, Object> param) throws Exception {

        ArticleReviewDTO articleReviewDTO = reviewMapper.findReviewLikeCntById(param);
        articleReviewDTO.setRwImg(reviewMapper.findReviewDetailImgById(param));

        if (articleReviewDTO.getRwImg().isEmpty()) {
            throw new NotFoundException("Is ot exist review");
        }

        return articleReviewDTO;
    }

}
