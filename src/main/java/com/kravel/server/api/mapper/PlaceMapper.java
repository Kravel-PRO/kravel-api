package com.kravel.server.api.mapper;

import com.kravel.server.api.dto.article.PlaceDTO;
import com.kravel.server.api.dto.article.PlaceMapDTO;
import com.kravel.server.api.dto.celebrity.PlaceRelatedCelebrityDTO;
import com.kravel.server.api.dto.media.PlaceRelatedMediaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PlaceMapper {

    public List<PlaceMapDTO> findAllPlaces(@Param("param") Map<String, Object> param);

    public PlaceDTO findPlaceById(@Param("param") Map<String, Object> param);


    public int checkExistArticleScrap(@Param("param") Map<String, Object> param);

    public int savePlaceScrap(@Param("param") Map<String, Object> param);

    public int removePlaceScrap(@Param("param") Map<String, Object> param);

//    public List<RawPlaceRelatedCelebrityDTO> findAllCelebrityByPlace(@Param("param") Map<String, Object> param);

    public List<PlaceRelatedMediaDTO> findAllPlaceAndPlaceInfoByMedia(@Param("param") Map<String, Object> param);

    public List<PlaceRelatedCelebrityDTO> findAllPlaceAndPlaceInfoByCelebrity(@Param("param") Map<String, Object> param);

}
