package com.kravel.server.api.article.service;

import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.dto.ArticleReviewListDTO;
import com.kravel.server.api.article.mapper.ArticleMapper;
import com.kravel.server.api.article.mapper.ReviewMapper;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private S3Uploader s3Uploader;

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

    public String saveReview(List<MultipartFile> files, int represent) throws Exception {

        List<String> imgUrlList = (List<String>) files.stream().map(file -> s3Uploader.upload(file, "review"));

        return "adsf";
    }

}
