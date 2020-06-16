package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.dto.article.ArticleDetailDTO;
import com.kravel.server.api.article.dto.article.ArticleMapDTO;
import com.kravel.server.api.article.dto.article.ArticleReviewImgDTO;
import com.kravel.server.api.article.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.article.dto.review.ImgDTO;
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

    public int checkExistArticleScrap(@Param("param") Map<String, Object> param);

    public int saveArticleScrap(@Param("param") Map<String, Object> param);

    public int removeArticleScrap(@Param("param") Map<String, Object> param);

    public List<ImgDTO> findAllArticleImg(@Param("param") Map<String, Object> param);

    public List<ArticleReviewImgDTO> findAllReviewImgs(@Param("param") Map<String, Object> param);
}
