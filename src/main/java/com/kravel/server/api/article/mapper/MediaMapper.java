package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.dto.media.MediaListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface MediaMapper {

    public MediaListDTO findAllMedia(@Param("param")Map<String, Object> param);
}
