package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.Model.Article;
import com.kravel.server.api.article.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ArticleMapper {

    public List<Article> findAllPlaces(@Param("param") Map<String, Object> param);

    public ArticleDetailDTO findPlaceByArticleId(@Param("param") Map<String, Object> param);

    public List<CelebrityDTO> findCelebrityListByArticleId(@Param("param") Map<String, Object> param);

    public List<ArticleReviewListDTO> findReviewListByArticleId(@Param("param") Map<String, Object> param);

    public List<RwImgDTO> findReviewDetailImgByReviewId(@Param("param") Map<String, Object> param);

    public ArticleReviewDTO findReviewLikeCntByReviewId(@Param("param") Map<String, Object> param);
}
