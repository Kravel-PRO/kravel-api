package com.kravel.server.mapper;

import com.kravel.server.model.media.Media;
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
