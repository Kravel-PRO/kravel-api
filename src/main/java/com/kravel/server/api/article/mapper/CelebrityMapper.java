package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.Model.Celebrity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CelebrityMapper {

    public List<Celebrity> findAllCelebrities(@Param("param") Map<String, Object> param);
}
