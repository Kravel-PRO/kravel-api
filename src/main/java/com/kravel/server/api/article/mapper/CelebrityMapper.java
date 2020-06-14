package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.article.dto.celebrity.ExCelebrityArticleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CelebrityMapper {

    public List<CelebrityDTO> findAllCelebrities(@Param("param") Map<String, Object> param);

    public CelebrityDTO findCelebrityById(@Param("param") Map<String, Object> param);

    public ExCelebrityArticleDTO findAllArticleByCelebrity(@Param("param") Map<String, Object> param);

}
