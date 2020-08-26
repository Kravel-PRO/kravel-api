package com.kravel.server.api.mapper;

import com.kravel.server.api.dto.review.ReviewDetailDTO;
import com.kravel.server.api.dto.review.ReviewOverviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ReviewMapper {

    public List<ReviewOverviewDTO> findAllReviews(@Param("param") Map<String, Object> param);

    public ReviewDetailDTO findReviewDetailById(@Param("param") Map<String, Object> param);

    public int saveReview(@Param("param") Map<String, Object> param);

    public int checkExistReviewLike(@Param("param") Map<String, Object> param);

    public int saveReviewLike(@Param("param") Map<String, Object> param);

    public int removeReviewLike(@Param("param") Map<String, Object> param);
}
