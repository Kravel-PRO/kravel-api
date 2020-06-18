package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.dto.review.ImgDTO;
import com.kravel.server.api.article.model.ArticleReview;
import com.kravel.server.api.article.dto.review.ReviewDTO;
import com.kravel.server.api.article.dto.review.ArticleReviewListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ReviewMapper {

    public List<ArticleReviewListDTO> findAllReviews(@Param("param") Map<String, Object> param);

    public List<ArticleReviewListDTO> findAllCelebrityReviews(@Param("param") Map<String, Object> param);

    public List<ImgDTO> findReviewDetailImgById(@Param("param") Map<String, Object> param);

    public ReviewDTO findReviewLikeCntById(@Param("param") Map<String, Object> param);

    public ArticleReview findArticleReviewById(@Param("param") Map<String, Object> param);

    public int saveReview(@Param("param") Map<String, Object> param);

    public int saveReviewImg(@Param("param") Map<String, Object> param);

    public int checkExistReviewLike(@Param("param") Map<String, Object> param);

    public int saveReviewLike(@Param("param") Map<String, Object> param);

    public int removeReviewLike(@Param("param") Map<String, Object> param);
}
