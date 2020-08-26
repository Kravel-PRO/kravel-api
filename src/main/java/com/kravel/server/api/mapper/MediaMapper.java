package com.kravel.server.api.mapper;

import com.kravel.server.api.dto.media.RawPlaceRelatedMediaDTO;
import com.kravel.server.api.model.Media;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MediaMapper {

    public List<Media> findAllMedia(@Param("param") Map<String, Object> param);

    public Media findMediaById(@Param("param") Map<String, Object> param);

}
