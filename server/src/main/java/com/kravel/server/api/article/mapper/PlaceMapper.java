package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.Model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PlaceMapper {

    public List<Article> findAllPlaces(@Param("param") Map<String, Object> param);

    public Article findPlaceByArticleId(@Param("param") Map<String, Object> param);
}
