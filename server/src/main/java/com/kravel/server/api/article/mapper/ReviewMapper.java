package com.kravel.server.api.article.mapper;

import com.kravel.server.api.article.dto.ArticleReviewDTO;
import com.kravel.server.api.article.dto.RwImgDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ReviewMapper {

    public List<RwImgDTO> findReviewDetailImgById(@Param("param") Map<String, Object> param);

    public ArticleReviewDTO findReviewLikeCntById(@Param("param") Map<String, Object> param);
}
