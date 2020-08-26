package com.kravel.server.api.mapper;

import com.kravel.server.api.dto.article.ArticleDetailDTO;
import com.kravel.server.api.dto.article.ArticleMapDTO;
import com.kravel.server.api.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.dto.celebrity.RawPlaceRelatedCelebrityDTO;
import com.kravel.server.api.dto.media.RawPlaceRelatedMediaDTO;
import com.kravel.server.api.dto.review.ImgDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PlaceMapper {

    public List<ArticleMapDTO> findAllPlaces(@Param("param") Map<String, Object> param);

    public ArticleDetailDTO findPlaceById(@Param("param") Map<String, Object> param);

    public List<CelebrityDTO> findAllCelebrities(@Param("param") Map<String, Object> param);

    public int checkExistArticleScrap(@Param("param") Map<String, Object> param);

    public int savePlaceScrap(@Param("param") Map<String, Object> param);

    public int removePlaceScrap(@Param("param") Map<String, Object> param);

    public RawPlaceRelatedCelebrityDTO findAllPlaceByCelebrity(@Param("param") Map<String, Object> param);

    public List<RawPlaceRelatedMediaDTO> findAllPlaceByMedia(@Param("param") Map<String, Object> param);

}
