package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.article.dto.media.MediaInfoDTO;
import com.kravel.server.api.article.dto.media.MediaListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MediaMapper {

    public List<MediaListDTO> findAllMedia(@Param("param") Map<String, Object> param);

    public MediaInfoDTO findMediaInfoById(@Param("param") Map<String, Object> param);
}
