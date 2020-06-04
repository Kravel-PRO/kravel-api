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

    public List<ArticleMapDTO> findAllPlaces(@Param("param") Map<String, Object> param);

    public ArticleDetailDTO findPlaceById(@Param("param") Map<String, Object> param);

    public List<CelebrityDTO> findAllCelebrities(@Param("param") Map<String, Object> param);

    public List<ArticleReviewListDTO> findAllReviews(@Param("param") Map<String, Object> param);

    public int checkExistArticleScrap(@Param("param") Map<String, Object> param);

    public int saveArticleScrap(@Param("param") Map<String, Object> param);

    public int removeArticleScrap(@Param("param") Map<String, Object> param);


}
