package com.kravel.server.api.article.service;

import com.kravel.server.api.article.Model.ArticleReview;
import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.dto.ArticleReviewListDTO;
import com.kravel.server.api.article.mapper.ArticleMapper;
import com.kravel.server.api.article.mapper.ReviewMapper;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    public List<String> saveReviewToS3(List<MultipartFile> files) throws Exception {
        List<String> imgUrlList = new ArrayList<>();

        for (MultipartFile file : files) {
            imgUrlList.add(s3Uploader.upload(file, "review"));
        }
        if (imgUrlList.isEmpty()) {
            throw new IOException("Af");
        }

        return imgUrlList;
    }

    public boolean saveReviewToDatabase(Map<String, Object> param) throws Exception {

        ArticleReview articleReview = reviewMapper.findArticleReviewById(param);

        param.put("mediaId", articleReview.getMediaId());
        param.put("celebrityId", articleReview.getCelebrityId());

        reviewMapper.saveReview(param);
        return reviewMapper.saveReviewImg(param) != 0;
    }

    public boolean handleReviewLike(Map<String, Object> param) throws Exception {
        int savedLike = reviewMapper.checkExistReviewLike(param);

        if ((boolean) param.get("likeState") && savedLike == 0) {
            return reviewMapper.saveReviewLike(param) != 0;

        } else if (savedLike >= 1) {
            return reviewMapper.removeReviewLike(param) != 0;

        } else  {
            throw new InvalidRequestException("It is not valid likeState");
        }
    }

}
